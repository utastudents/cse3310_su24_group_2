package uta.cse3310;

public class App {
    public static void main(String[] args) {
        int httpPort = Integer.parseInt(System.getenv().getOrDefault("HTTP_PORT", "9180"));
        int websocketPort = httpPort + 100;

        Game game = new Game();
        WebSocket webSocketServer = new WebSocket(websocketPort, game);


    }
}


