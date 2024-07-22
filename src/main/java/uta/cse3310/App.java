package uta.cse3310;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class App {
    private static Vector<GameSession> activeGames = new Vector<>();

    public static void main(String[] args) {
        // Read the HTTP port 
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;


        int websocketPort = httpPort + 100;

        // This starts the HTTP server
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(httpPort), 0);
            httpServer.createContext("/", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String filePath = "src/main/resources/index.html";
                    byte[] response = Files.readAllBytes(Paths.get(filePath));
                    exchange.sendResponseHeaders(200, response.length);
                    exchange.getResponseBody().write(response);
                    exchange.getResponseBody().close();
                }
            });
            httpServer.start();
            System.out.println("HTTP server started on port " + httpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // starts the WebSocket server
        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();
        System.out.println("WebSocket server started on port " + websocketPort);
    }
}

