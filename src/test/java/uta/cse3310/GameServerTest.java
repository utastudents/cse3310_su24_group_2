package uta.cse3310;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameServerTest {
    private GameServer gameServer;

    @BeforeEach
    public void setUp() {
        gameServer = new GameServer(8080, 9000);
    }

    @Test
    public void testStart() {
        gameServer.start();
       
    }

    @Test
    public void testFindOrCreateGameSession() {
        GameSession session = gameServer.findOrCreateGameSession();
        assertNotNull(session);
        assertTrue(gameServer.getGameSessions().contains(session));
    }

    @Test
    public void testRemoveGameSession() {
        GameSession session = gameServer.findOrCreateGameSession();
        gameServer.removeGameSession(session);
        assertFalse(gameServer.getGameSessions().contains(session));
    }

    @Test
    public void testUpdateScoreboard() {
        Player player = new Player("1", "Rakshya");
        gameServer.updateScoreboard(player);
        assertTrue(gameServer.getTopPlayers().contains(player));
    }

    @Test
    public void testRemovePlayerFromSession() {
        GameSession session = gameServer.findOrCreateGameSession();
        Player player = new Player("1", "Rakshya");
        session.addPlayer(player);
        gameServer.removePlayerFromSession(player.getPlayerId());
        assertFalse(session.getPlayers().contains(player));
    }

    @Test
    public void testGetPlayerCount() {
        GameSession session = gameServer.findOrCreateGameSession();
        Player player1 = new Player("1", "Rakshya");
        Player player2 = new Player("2", "Chris");
        session.addPlayer(player1);
        session.addPlayer(player2);
        assertEquals(2, gameServer.getPlayerCount());
    }
}
