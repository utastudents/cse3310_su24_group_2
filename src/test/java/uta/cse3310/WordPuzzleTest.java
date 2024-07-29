package uta.cse3310;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class WordPuzzleTest {

    private WordPuzzle puzzle;

    @BeforeEach
    void setUp() {
        puzzle = new WordPuzzle();
    }

    @Test
    void testGeneratePuzzle() {
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

    @Test
    void testCheckSolved() {
        puzzle.generatePuzzle(2);
        String solution = puzzle.getCurrentPuzzle();
        
        assertTrue(puzzle.checkSolved(solution));
        assertFalse(puzzle.checkSolved("wrong solution"));
    }

    @Test
    void testLoadWords() {
      
        assertDoesNotThrow(() -> puzzle.loadWords("src/main/resources/words.txt"));
    }
}
