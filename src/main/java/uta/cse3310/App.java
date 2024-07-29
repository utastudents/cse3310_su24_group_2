//App.java starts the HTTP and WebSocket servers.
package uta.cse3310;

public class App {
    public static void main(String[] args) {
        String httpPortStr = System.getenv("HTTP_PORT");
        int httpPort = (httpPortStr != null) ? Integer.parseInt(httpPortStr) : 9080;

        String websocketPortStr = System.getenv("WEBSOCKET_PORT");
        int websocketPort = (websocketPortStr != null) ? Integer.parseInt(websocketPortStr) : httpPort + 100;

        GameServer gameServer = new GameServer(httpPort, websocketPort);
        gameServer.start();

        System.out.println("GameServer started on HTTP port " + httpPort + " and WebSocket port " + websocketPort);
    }
}
