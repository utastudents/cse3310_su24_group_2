package uta.cse3310;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONObject;
import java.net.InetSocketAddress;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebSocketHandlerTest {

    @Mock
    private GameServer mockGameServer;
    
    @Mock
    private WebSocket mockConn;

    private WebSocketHandler handler;

    @BeforeEach
    void setUp() {
        handler = new WebSocketHandler(8080, mockGameServer);
        // Remove this line as it's not necessary for all tests
        // when(mockConn.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 8080));
    }

    @Test
    void testOnOpen() {
        ClientHandshake mockHandshake = mock(ClientHandshake.class);
        when(mockConn.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 8080));
        handler.onOpen(mockConn, mockHandshake);
        verify(mockConn).getRemoteSocketAddress();
    }

    @Test
    void testOnMessage_JoinGame() throws Exception {
        GameSession mockSession = mock(GameSession.class);
        when(mockGameServer.findOrCreateGameSession()).thenReturn(mockSession);
        when(mockGameServer.getPlayerCount()).thenReturn(0);
        when(mockConn.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 8080));

        String message = new JSONObject().put("Action", "JOIN_GAME").toString();
        handler.onMessage(mockConn, message);

        verify(mockGameServer).findOrCreateGameSession();
        verify(mockSession).addPlayer(any(Player.class));
        verify(mockConn).send(anyString());
    }

    @Test
    void testOnClose() {
        when(mockConn.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 8080));
        handler.onClose(mockConn, 1000, "Test reason", true);
        verify(mockGameServer).removePlayerFromSession(anyString());
    }

    @Test
    void testOnError() {
        Exception mockException = new Exception("Test exception");
        handler.onError(mockConn, mockException);
        
    }
}
