package uta.cse3310;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

    @Test
    void testHttpServerCreation() {
        HttpServer server = new HttpServer(8080, "src/main/resources");
        assertNotNull(server);
    }
}
