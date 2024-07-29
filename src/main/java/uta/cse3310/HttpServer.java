package uta.cse3310;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServer {
    private int port;
    private String htmlDir;
    private HttpServer server;

    public HttpServer(int port, String htmlDir) {
        this.port = port;
        this.htmlDir = htmlDir;
    }

    public void start() {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticFileHandler(htmlDir));
        server.setExecutor(null);
        server.start();
        System.out.println("HTTP Server started on port " + port);
    }

    private void setExecutor(Object object) {
        throw new UnsupportedOperationException("Unimplemented method 'setExecutor'");
    }

    private void createContext(String string, StaticFileHandler staticFileHandler) {
        throw new UnsupportedOperationException("Unimplemented method 'createContext'");
    }

    private static HttpServer create(InetSocketAddress inetSocketAddress, int i) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public void stop() {
        if (server != null) {
            server.stop();
        }
    }

    static class StaticFileHandler implements HttpHandler {
        private String htmlDir;

        public StaticFileHandler(String htmlDir) {
            this.htmlDir = htmlDir;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Implement file serving logic
        }
    }
}

