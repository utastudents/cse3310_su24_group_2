//GameServer controls the  server; sessions and scores.
package uta.cse3310;

import java.util.List;
import java.util.ArrayList;

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
        webSocketHandler.start();
    }

    public void stop() {
        // to stop the server
    }

    public void createGameSession(int numberOfPlayers) {
        GameSession session = new GameSession();
        gameSessions.add(session);
    }

    public void removeGameSession(GameSession session) {
        gameSessions.remove(session);
    }

    public void updateScoreboard(Player player) {
        scoreboard.updateScoreboard(player);
    }

    public List<Player> getTopPlayers() {
        return scoreboard.getTopPlayers();
    }
}

