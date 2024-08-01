package uta.cse3310;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
  @Test
  void testPlayerConstructorAndUpdateScore() {
    String playerId = "1";
    String playerName = "TestPlayer";
    Player player = new Player(playerId, playerName);


    assertEquals(playerId, player.getPlayerId(), "Player ID mismatch");
    assertEquals(playerName, player.getName(), "Player name mismatch");


    int initialScore = player.getScore();
    int scoreToAdd = 100;
    player.updateScore(scoreToAdd);
    assertEquals(initialScore + scoreToAdd, player.getScore(), "Score update incorrect");
  }
}
