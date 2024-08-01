//GameServer controls the  server; sessions and scores.

package uta.cse3310;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class GameServer {
    private int httpPort;
    private int wsPort;
    private Map<String, GameSession> lobbies;
    private Scoreboard scoreboard;
    private Websocket websocket;

    public GameServer(int httpPort, int wsPort) {
        this.httpPort = httpPort;
        this.wsPort = wsPort;
        this.lobbies = new HashMap<>();
        this.websocket = new Websocket(wsPort, this);
    }

    public void start() {
        websocket.start();
    }

    public void createLobby(String lobbyId) {
        if (!lobbies.containsKey(lobbyId)) {
            lobbies.put(lobbyId, new GameSession(lobbyId));
        }
    }

    public GameSession joinLobby(String lobbyId, Player player) {
        GameSession session = lobbies.get(lobbyId);
        if (session != null && session.getPlayers().size() < 4) {
            session.addPlayer(player);
            return session;
        }
        return null;
    }

    public List<String> getAvailableLobbies() {
        return lobbies.entrySet().stream()
                .filter(entry -> entry.getValue().getPlayers().size() < 4)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

public void removePlayerFromSession(String playerId) {
for (GameSession session : lobbies.values()) {
Player playerToRemove = session.getPlayers().stream()
.filter(p -> p.getPlayerId().equals(playerId))
.findFirst()
.orElse(null);
if (playerToRemove != null) {
session.removePlayer(playerToRemove);
break;
 }
 }
}



    public GameSession findSessionForPlayer(String playerId) {
for (GameSession session : lobbies.values()) {
if (session.getPlayers().stream().anyMatch(p -> p.getPlayerId().equals(playerId))) {
return session;
}
}
return null;
}


    public void updateScoreboard(Player player) {
scoreboard.updateScoreboard(player);
}

   

    public List<Player> getTopPlayers() {
return scoreboard.getTopPlayers();
 }

  

    public int getPlayerCount() {
return lobbies.values().stream().mapToInt(session -> session.getPlayers().size()).sum();
 }

 

    public Websocket getWebsocket() {
return websocket;
}
}
