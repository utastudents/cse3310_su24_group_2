package uta.cse3310;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class WebSocketHandler extends WebSocketServer {

    public WebSocketHandler(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message from " + conn.getRemoteSocketAddress() + ": " + message);
        conn.send("Message received: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error connecting " + conn.getRemoteSocketAddress() + ": " + ex);
    }

    @Override
    public void onStart() {
        System.out.println("The WebSocket server has started successfully on port " + getPort());
    }

    public static void main(String[] args) {
        int port = 9080; 
        WebSocketHandler server = new WebSocketHandler(port);
        server.start();
        System.out.println("WebSocket server started on port " + port);
    }
}

