//Handles WebSocket connections and messages, comp error when class name is just websocket.java
package uta.cse3310;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends WebSocketServer {
    private static Set<WebSocket> conns = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private GameServer gameServer;

    public WebSocketHandler(int port, GameServer gameServer) {
        super(new InetSocketAddress(port));
        this.gameServer = gameServer;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ": " + message);


        for (WebSocket sock : conns) {
            sock.send(message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("Error occurred: " + ex.getMessage());
        if (conn != null) {
            conns.remove(conn);
        }
    }

    @Override
    public void onStart() {
        System.out.println("WebSocket server started successfully.");
    }
}

