//App.java starts the HTTP and WebSocket servers.
package uta.cse3310;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;

        String htmlDir = "src/main/resources"; 

        HttpServer httpServer = new HttpServer(httpPort, htmlDir);
        try {
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int websocketPort = httpPort + 100;

        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();

        System.out.println("WebSocket server started on port " + websocketPort);
    }
}
