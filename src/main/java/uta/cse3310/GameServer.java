package uta.cse3310;

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
        this.webSocketHandler = new WebSocketHandler(wsPort);
    }

    public void start() {
        webSocketHandler.start();
    }

    public void stop() {

    }

    public void createGameSession(int numberOfPlayers) {

    }

    public void removeGameSession(GameSession session) {
       
    }

    public void updateScoreboard(Player player) {

    }
}

