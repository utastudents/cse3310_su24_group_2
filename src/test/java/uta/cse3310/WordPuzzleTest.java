package uta.cse3310;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordPuzzleTest {

  @Test
  void testGeneratePuzzle() {
    WordPuzzle puzzle = new WordPuzzle();
    puzzle.generatePuzzle(2);
    String displayedPuzzle = puzzle.getDisplayedPuzzle();
    String currentPuzzle = puzzle.getCurrentPuzzle();
   
    assertNotNull(displayedPuzzle);
    assertNotNull(currentPuzzle);
    assertTrue(displayedPuzzle.matches("^[_ ]+$"));
    assertEquals(displayedPuzzle.length(), currentPuzzle.length());
  }

  @Test
  void testRevealLetter() {
    WordPuzzle puzzle = new WordPuzzle();
    puzzle.generatePuzzle(2);
    String before = puzzle.getDisplayedPuzzle();
    boolean revealed = puzzle.revealLetter('e');
    String after = puzzle.getDisplayedPuzzle();
   
    if (revealed) {
      assertNotEquals(before, after);
      assertTrue(after.contains("e"));
    } else {
      assertEquals(before, after);
    }
  }
}