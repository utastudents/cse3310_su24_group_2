package uta.cse3310;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameSessionTest {

@Test
void testAddPlayerAndGetLobbyId() {
String testLobbyId = "testLobby";
GameSession gameSession = new GameSession(testLobbyId);


assertEquals(testLobbyId, gameSession.getLobbyId(), "Lobby ID should match the one provided in constructor");


Player testPlayer = new Player("1", "TestPlayer");
gameSession.addPlayer(testPlayer);

assertTrue(gameSession.getPlayers().contains(testPlayer), "Added player should be in the game session");
assertEquals(1, gameSession.getPlayers().size(), "Game session should have one player after adding");
}
}
