//App.java starts the HTTP and WebSocket servers.
package uta.cse3310;

public class App {
    public static void main(String[] args) {
        
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;

        String htmlDir = "src/main/resources"; 

        HttpServer httpServer = new HttpServer(httpPort, htmlDir);
        httpServer.start();

        int websocketPort = httpPort + 100;

        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();

        System.out.println("WebSocket server started on port " + websocketPort);
    }
}

