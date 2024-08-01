package uta.cse3310;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) {
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9002;

        String wsPortStr = System.getenv("WEBSOCKET_PORT");
        int websocketPort = (wsPortStr != null) ? Integer.parseInt(wsPortStr) : 9102;

        String htmlDir = "src/main/resources";

        HttpServer httpServer = new HttpServer(httpPort, htmlDir);
        httpServer.start();

        GameServer gameServer = new GameServer(httpPort, websocketPort);
       
        WebSocketServer webSocketServer = new WebSocketServer(new InetSocketAddress(websocketPort)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
                gameServer.getWebsocket().onOpen(conn, handshake);
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
                gameServer.getWebsocket().onClose(conn, code, reason, remote);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                gameServer.getWebsocket().onMessage(conn, message);
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                System.err.println("Error occurred on connection " + conn);
                ex.printStackTrace();
                gameServer.getWebsocket().onError(conn, ex);
            }

            @Override
            public void onStart() {
                System.out.println("WebSocket server started on port: " + websocketPort);
            }
        };

        webSocketServer.start();
        gameServer.start();

        System.out.println("HTTP server started on port " + httpPort);
        System.out.println("WebSocket server started on port " + websocketPort);
    }
}
