package uta.cse3310;

public class App {
    public static void main(String[] args) {
        int httpPort = Integer.parseInt(System.getenv().getOrDefault("HTTP_PORT", "9180"));
        int websocketPort = httpPort + 100;

        GameSession gameSession = new GameSession(); 
        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();
    }
}

