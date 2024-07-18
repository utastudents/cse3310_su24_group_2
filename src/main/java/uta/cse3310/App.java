package uta.cse3310;

public class App {
    public static void main(String[] args) {
        int websocketPort = 9080; // We'll hard code the websocket

//will create a new session websocket
        GameSession gameSession = new GameSession(); 
        WebSocketHandler webSocketServer = new WebSocketHandler(websocketPort);
        webSocketServer.start();

        System.out.println("WebSocket server started on port " + websocketPort);
    }
}

