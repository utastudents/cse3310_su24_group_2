package uta.cse3310;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {
    private GameSession gameSession;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        gameSession = new GameSession();
        player1 = new Player("1", "Rakshya");
        player2 = new Player("2", "Chris");
        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.startGame();
    }

    @Test
    public void testBuyVowel() {
        int initialScore = player1.getScore();
        gameSession.buyVowel(player1.getPlayerId());
        // Assuming the cost is 250 and the initial score is set to 1000
        int expectedScore = initialScore - 250;
        assertEquals(expectedScore, player1.getScore(), "Player's score should be adjusted after buying a vowel.");
    }

    @Test
    public void testSelectConsonant() {
        // We need to simulate or mock a puzzle where the consonant is present
        // Assuming the puzzle generated has the consonant "T" in it
        gameSession.selectConsonant(player1.getPlayerId(), "T");
        // Verify if the score has increased based on stake and occurrences of 'T'
        int occurrences = 1; // Example value; this depends on the actual puzzle
        int stake = gameSession.getCurrentStake();
        int expectedScore = player1.getScore() + (stake * occurrences);
        assertEquals(expectedScore, player1.getScore(), "Player's score should increase after selecting a consonant.");
    }

    @Test
    public void testNextTurn() {
        gameSession.nextTurn();
        assertEquals(player2, gameSession.getCurrentPlayer(), "The next turn should switch to the next player.");
    }

    @Test
    public void testEndRound() {
        gameSession.endRound();
        assertEquals(2, gameSession.getRound(), "The round number should increment after ending a round.");
    }

    @Test
    public void testEndGame() {
        // Complete the rounds to end the game
        for (int i = 0; i < 3; i++) {
            gameSession.endRound();
        }
        assertFalse(gameSession.getPlayers().contains(player1), "Player should be notified or removed after the game ends.");
    }

    @Test
    public void testGetCurrentPlayer() {
        assertEquals(player1, gameSession.getCurrentPlayer(), "The current player should be the first player added.");
    }

    @Test
    public void testGetCurrentPuzzle() {
        assertNotNull(gameSession.getCurrentPuzzle(), "The current puzzle should not be null.");
    }

    @Test
    public void testGetCurrentStake() {
        // Assuming stakes are randomized; test for a range
        int currentStake = gameSession.getCurrentStake();
        assertTrue(currentStake >= 100 && currentStake <= 1000, "Current stake should be within the predefined range.");
    }

    @Test
    public void testGetRound() {
        assertEquals(1, gameSession.getRound(), "The round number should be 1 at the beginning of the game.");
    }

    @Test
    public void testGetPlayers() {
        assertEquals(2, gameSession.getPlayers().size(), "There should be 2 players in the game.");
    }

    @Test
    public void testGetGameStatus() {
        assertEquals("Game in progress", gameSession.getGameStatus(), "The game status should reflect that the game is in progress.");
    }
}
