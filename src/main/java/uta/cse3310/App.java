//App.java starts the HTTP and WebSocket servers.
package uta.cse3310;

public class App {
    public static void main(String[] args) {
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;

        String wsPortStr = System.getenv("WEBSOCKET_PORT");
        int websocketPort = (wsPortStr != null) ? Integer.parseInt(wsPortStr) : (httpPort + 100);

        String htmlDir = "src/main/resources";

        HttpServer httpServer = new HttpServer(httpPort, htmlDir);
        httpServer.start();

        GameServer gameServer = new GameServer(httpPort, websocketPort);
        gameServer.start();

        System.out.println("HTTP server started on port " + httpPort);
        System.out.println("WebSocket server started on port " + websocketPort);
    }
}
