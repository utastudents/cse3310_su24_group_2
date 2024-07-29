//GameServer controls the  server; sessions and scores.
package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private int httpPort;
    private int wsPort;
    private List<GameSession> gameSessions;
    private Scoreboard scoreboard;
    private WebSocketHandler webSocketHandler;

    public GameServer(int httpPort, int wsPort) {
        this.httpPort = httpPort;
        this.wsPort = wsPort;
        this.gameSessions = new ArrayList<>();
        this.scoreboard = new Scoreboard();
        this.webSocketHandler = new WebSocketHandler(wsPort);
    }

    public void start() {
        try{
        webSocketHandler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }   
     }

     public void stop() {
        try {
            webSocketHandler.stop();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception, perhaps by logging it or notifying the user
        }
    }
    public void createGameSession(int numberOfPlayers) {
        if (numberOfPlayers >= 2 && numberOfPlayers <= 4) {
            GameSession session = new GameSession(numberOfPlayers);
            gameSessions.add(session);
            // Notify WebSocket clients of the new session
            webSocketHandler.broadcast("New session created with " + numberOfPlayers + " players.");
        } else {
            throw new IllegalArgumentException("Number of players must be between 2 and 4.");
        }
    }

    public void removeGameSession(GameSession session) {
        gameSessions.remove(session);
        // Notify WebSocket clients of the session removal
        webSocketHandler.broadcast("Session removed.");
    }

    public void updateScoreboard(Player player, int points) {
        scoreboard.updateScore(player.getPlayerId(), points);
    }

    public List<Player> getTopPlayers() {
        return scoreboard.getTopPlayers();
    }
}
