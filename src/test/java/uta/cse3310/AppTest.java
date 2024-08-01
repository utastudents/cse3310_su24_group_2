package uta.cse3310;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

  @Test
  public void testMain() {
    // Test case 1: Verify that the main method runs without any exceptions
    assertDoesNotThrow(() -> App.main(new String[]{}));

    // Test case 2: Verify that the main method handles command line arguments correctly
    String[] args = {"arg1", "arg2"};
    assertDoesNotThrow(() -> App.main(args));
  }
}