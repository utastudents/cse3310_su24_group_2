package uta.cse3310;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONObject;
import static org.mockito.Mockito.*;
import java.net.InetSocketAddress;

class WebSocketHandlerTest {

    private GameServer mockGameServer;
    private WebSocketHandler handler;
    private WebSocket mockConn;

    @BeforeEach
    void setUp() {
        mockGameServer = mock(GameServer.class);
        handler = new WebSocketHandler(8080, mockGameServer);
        mockConn = mock(WebSocket.class);
        when(mockConn.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", 8080));
    }

    @Test
    void testOnOpen() {
        ClientHandshake mockHandshake = mock(ClientHandshake.class);
        handler.onOpen(mockConn, mockHandshake);
        verify(mockConn).getRemoteSocketAddress();
    }

    @Test
    void testOnMessage_JoinGame() throws Exception {
        GameSession mockSession = mock(GameSession.class);
        when(mockGameServer.findOrCreateGameSession()).thenReturn(mockSession);
        when(mockGameServer.getPlayerCount()).thenReturn(0);

        String message = new JSONObject().put("Action", "JOIN_GAME").toString();
        handler.onMessage(mockConn, message);

        verify(mockGameServer).findOrCreateGameSession();
        verify(mockSession).addPlayer(any(Player.class));
        verify(mockConn).send(anyString());
    }

    @Test
    void testOnClose() {
        handler.onClose(mockConn, 1000, "Test reason", true);
        verify(mockGameServer).removePlayerFromSession(anyString());
    }

    @Test
    void testOnError() {
        Exception mockException = new Exception("Test exception");
        handler.onError(mockConn, mockException);
        
    }
}
