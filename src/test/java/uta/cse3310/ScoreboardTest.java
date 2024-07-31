package uta.cse3310;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ScoreboardTest {
    
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void testUpdateScoreboard() {
        Player player1 = new Player("1", "Player1");
        player1.updateScore(100);
        scoreboard.updateScoreboard(player1);

        List<Player> topPlayers = scoreboard.getTopPlayers();
        assertEquals(1, topPlayers.size());
        assertEquals("Player1", topPlayers.get(0).getName());
        assertEquals(100, topPlayers.get(0).getScore());
    }

    @Test
    void testTopPlayersLimit() {
        for (int i = 0; i < 15; i++) {
            Player player = new Player(String.valueOf(i), "Player" + i);
            player.updateScore(i * 100);
            scoreboard.updateScoreboard(player);
        }

        List<Player> topPlayers = scoreboard.getTopPlayers();
        assertEquals(10, topPlayers.size());
        assertEquals(1400, topPlayers.get(0).getScore());
    }
}
