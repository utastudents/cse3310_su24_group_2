package uta.cse3310;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;

public class WebSocket extends WebSocketServer {

    public WebSocket(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connectionId++;
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

        ServerEvent E = new ServerEvent();
        Game G = new Game();
        conn.setAttachment(G);

        Gson gson = new Gson();
        String jsonString = gson.toJson(E);
        conn.send(jsonString);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
        Game G = conn.getAttachment();
        G = null;
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);

        Gson gson = new Gson();
        UserEvent U = gson.fromJson(message, UserEvent.class);

        Game G = conn.getAttachment();
        G.Update(U);

        String jsonString = gson.toJson(G);
        broadcast(jsonString);
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(0);

    }

    public static void main(String[] args) {
        int port = 9180; 
        WebSocket server = new WebSocket(port);
        server.setReuseAddr(true);
        server.start();
        System.out.println("WebSocket server started on port: " + port);
    }
}

