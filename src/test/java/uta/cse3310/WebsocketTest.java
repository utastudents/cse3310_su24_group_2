package uta.cse3310;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.InetSocketAddress;

class WebsocketTest {

  @Test
  void testWebsocketCreationAndAddress() {
    int port = 8081;
    GameServer gameServer = new GameServer(8080, port);
    Websocket websocket = new Websocket(port, gameServer);


    assertNotNull(websocket, "Websocket should not be null");


    assertEquals(new InetSocketAddress(port), websocket.getAddress(), "Websocket mismatch");
  }
}