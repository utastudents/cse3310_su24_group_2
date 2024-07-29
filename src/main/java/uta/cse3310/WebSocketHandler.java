//handle WebSocket connections and messages.
package uta.cse3310;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.framing.CloseFrame;
import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketHandler extends WebSocketServer {
    private Set<WebSocket> connections;

    public WebSocketHandler(int port) {
        super(new InetSocketAddress(port));
        connections = new CopyOnWriteArraySet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from " + conn.getRemoteSocketAddress() + ": " + message);
        // Broadcast the message to all connections
        for (WebSocket connection : connections) {
            if (connection != conn) {
                connection.send(message);
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            connections.remove(conn);
            System.out.println("Error from " + conn.getRemoteSocketAddress() + ": " + ex.getMessage());
        } else {
            System.out.println("Server error: " + ex.getMessage());
        }
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started successfully.");
    }

    public void broadcast(String message) {
        for (WebSocket connection : connections) {
            connection.send(message);
        }
    }

    public void stopServer() {
        try {
            stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
