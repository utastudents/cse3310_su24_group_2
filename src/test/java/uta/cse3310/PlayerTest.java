package uta.cse3310;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class PlayerTest {
    private Player player;
    private GameSession gameSession;

    @BeforeEach
    public void setUp() {
        player = new Player("1", "Rakshya");
        gameSession = new GameSession();
    }

    @Test
    public void testJoinGame() {
        player.joinGame(gameSession);
        assertTrue(gameSession.getPlayers().contains(player), "Player should be in the game session after joining.");
       
    }

    @Test
    public void testLeaveGame() {
        player.joinGame(gameSession);
        player.leaveGame(gameSession);
        assertFalse(gameSession.getPlayers().contains(player), "Player should not be in the game session after leaving.");
    }


    @Test
    public void testUpdateScore() {
        player.updateScore(50);
        assertEquals(50, player.getScore(), "Player's score should be updated correctly.");
    }

    @Test
    public void testSetOnlineStatus() {
        player.setOnlineStatus(true);
       
        player.setOnlineStatus(false);
        
    }

    @Test
    public void testUpdatePuzzle() {
        String puzzle = "Sudoku";
        player.updatePuzzle(puzzle);
       
    }

    @Test
    public void testUpdateScoreboard() {
        List<Player> topPlayers = new ArrayList<>();
        Player player1 = new Player("456", "Alice");
        Player player2 = new Player("789", "Bob");
        topPlayers.add(player1);
        topPlayers.add(player2);
        player.updateScoreboard(topPlayers);
        
    }
}