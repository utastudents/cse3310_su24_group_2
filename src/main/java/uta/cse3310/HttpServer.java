package uta.cse3310;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.FileContextHandler;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;

public class HttpServer {

    private static final String HTML = "./html";
    private int port = 8080;
    private String dirname = HTML;

    public HttpServer(int portNum, String dirName) {
        this.port = portNum;
        this.dirname = dirName;
    }

    public void start() {
        try {
            File dir = new File(dirname);
            if (!dir.canRead()) {
                throw new FileNotFoundException(dir.getAbsolutePath());
            }
            // set up server
            HTTPServer server = new HTTPServer(port);
            VirtualHost host = server.getVirtualHost(null); // default host
            host.setAllowGeneratedIndex(true); // with directory index pages
            host.addContext("/", new FileContextHandler(dir));
            host.addContext("/api/time", new ContextHandler() {
                @Override
                public int serve(Request req, Response resp) throws IOException {
                    long now = System.currentTimeMillis();
                    resp.getHeaders().add("Content-Type", "text/plain");
                    resp.send(200, String.format("%tF %<tT", now));
                    return 0;
                }
            });
            server.start();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
    
}

