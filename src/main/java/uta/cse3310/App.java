package uta.cse3310;

import org.java_websocket.server.WebSocketServer;
import java.io.IOException;
import java.util.Vector;

public class App {
    private static Vector<GameSession> activeGames = new Vector<>();

    public static void main(String[] args) {
        // Read the HTTP port 
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;
        int websocketPort = httpPort + 100;

        // Start the HTTP server
        HttpServerHandler httpServerHandler = new HttpServerHandler(httpPort);
        try {
            httpServerHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start the WebSocket server
        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();
        System.out.println("WebSocket server started on port " + websocketPort);
    }
}
