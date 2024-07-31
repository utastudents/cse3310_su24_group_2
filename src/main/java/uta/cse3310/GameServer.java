//GameServer controls the  server; sessions and scores.

package uta.cse3310;

import java.util.List;
import java.util.ArrayList;

public class GameServer {
    private int httpPort;
    private int wsPort;
    private List<GameSession> gameSessions;
    private Scoreboard scoreboard;
    private Websocket websocket;

    public GameServer(int httpPort, int wsPort) {
        this.httpPort = httpPort;
        this.wsPort = wsPort;
        this.gameSessions = new ArrayList<>();
        this.scoreboard = new Scoreboard();
        this.websocket = new Websocket(wsPort, this);
    }

    public void start() {
        websocket.start();
    }

    public void stop() {
        // to stop the server
    }

    public GameSession findOrCreateGameSession() {
        for (GameSession session : gameSessions) {
            if (session.getPlayers().size() < 4) {
                return session;
            }
        }
        GameSession newSession = new GameSession();
        gameSessions.add(newSession);
        return newSession;
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

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public GameSession findSessionForPlayer(String playerId) {
        for (GameSession session : gameSessions) {
            for (Player player : session.getPlayers()) {
                if (player.getPlayerId().equals(playerId)) {
                    return session;
                }
            }
        }
        return null;
    }

    public void removePlayerFromSession(String playerId) {
        GameSession session = findSessionForPlayer(playerId);
        if (session != null) {
            Player playerToRemove = session.getPlayers().stream()
                    .filter(p -> p.getPlayerId().equals(playerId))
                    .findFirst()
                    .orElse(null);
            if (playerToRemove != null) {
                session.removePlayer(playerToRemove);
            }
        }
    }

    public int getPlayerCount() {
        return gameSessions.stream().mapToInt(session -> session.getPlayers().size()).sum();
    }
}
