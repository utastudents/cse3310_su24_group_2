
package uta.cse3310;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {

 @Test
void testHttpServerCreation() {
 int port = 8080;
String htmlDir = "src/main/resources";
HttpServer server = new HttpServer(port, htmlDir);


 assertNotNull(server, "Server should not be null");
 }
}