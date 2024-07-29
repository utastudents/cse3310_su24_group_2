package uta.cse3310;

import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    private int port;
    private String htmlDir;
    private HTTPServer server;

    public HttpServer(int port, String htmlDir) {
        this.port = port;
        this.htmlDir = htmlDir;
        server = new HTTPServer(port);
    }

    public void start()  throws IOException { 
        VirtualHost host = server.getVirtualHost(null);
        host.addContext("/", this::create);
        server.start();
        System.out.println("HTTP Server started on port " + port);

    }


    public void stop() {
        server.stop();

}

public int create(Request req, Response resp) throws IOException {
    String path = htmlDir + req.getPath();
    if (Files.exists(Paths.get(path))) {
        resp.sendBody(Files.newInputStream(Paths.get(path)), Files.size(Paths.get(path)), null);
        return 0;
    } else {
        resp.send(404, "File not found");
        return 1;
    }
}
    }


