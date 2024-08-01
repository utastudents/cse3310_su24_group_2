package uta.cse3310;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class GameServerTest {

private GameServer gameServer;

@BeforeEach
void setUp() {
gameServer = new GameServer(8080, 8081);
}

@Test
void testCreateAndJoinLobby() {
String lobbyId = "testLobby";
gameServer.createLobby(lobbyId);

Player player1 = new Player("player1", "Player One");
GameSession session = gameServer.joinLobby(lobbyId, player1);

assertNotNull(session);
assertEquals(1, session.getPlayers().size());
assertEquals("Player One", session.getPlayers().get(0).getName());
}

@Test
void testRemovePlayerFromSession() {
String lobbyId = "testLobby";
gameServer.createLobby(lobbyId);

Player player1 = new Player("player1", "Player One");
gameServer.joinLobby(lobbyId, player1);

gameServer.removePlayerFromSession("player1");

GameSession session = gameServer.findSessionForPlayer("player1");
assertNull(session);
}

@Test
void testGetAvailableLobbies() {
gameServer.createLobby("lobby1");
gameServer.createLobby("lobby2");

List<String> lobbies = gameServer.getAvailableLobbies();

assertEquals(2, lobbies.size());
assertTrue(lobbies.contains("lobby1"));
assertTrue(lobbies.contains("lobby2"));
}

@Test
void testFindSessionForPlayer() {
String lobbyId = "testLobby";
gameServer.createLobby(lobbyId);

Player player1 = new Player("player1", "Player One");
gameServer.joinLobby(lobbyId, player1);

GameSession session = gameServer.findSessionForPlayer("player1");

assertNotNull(session);
assertEquals(lobbyId, session.getLobbyId());
}

@Test
void testUpdateScoreboard() {
Player player1 = new Player("player1", "Player One");
player1.updateScore(100);

gameServer.updateScoreboard(player1);

List<Player> topPlayers = gameServer.getTopPlayers();

assertEquals(1, topPlayers.size());
assertEquals("Player One", topPlayers.get(0).getName());
assertEquals(100, topPlayers.get(0).getScore());
}

@Test
void testGetPlayerCount() {
String lobbyId1 = "lobby1";
String lobbyId2 = "lobby2";
gameServer.createLobby(lobbyId1);
gameServer.createLobby(lobbyId2);

gameServer.joinLobby(lobbyId1, new Player("player1", "Player One"));
gameServer.joinLobby(lobbyId1, new Player("player2", "Player Two"));
gameServer.joinLobby(lobbyId2, new Player("player3", "Player Three"));

assertEquals(3, gameServer.getPlayerCount());
}
}