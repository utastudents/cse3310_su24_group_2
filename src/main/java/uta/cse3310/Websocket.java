package uta.cse3310;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.json.JSONObject;
import org.json.JSONException;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Websocket extends WebSocketServer {
  private static Set<WebSocket> conns = Collections.newSetFromMap(new ConcurrentHashMap<>());
  private GameServer gameServer;

  public Websocket(int port, GameServer gameServer) {
    super(new InetSocketAddress(port));
    this.gameServer = gameServer;
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    conns.add(conn);
    System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    conns.remove(conn);
    System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    gameServer.removePlayerFromSession(conn.getRemoteSocketAddress().toString());
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println("Message from " + conn.getRemoteSocketAddress().getAddress().getHostAddress() + ": " + message);
    try {
      JSONObject jsonMessage = new JSONObject(message);
      String action = jsonMessage.getString("Action");
      String playerId = conn.getRemoteSocketAddress().toString();
     
      switch (action) {
        case "CREATE_LOBBY":
          String lobbyId = jsonMessage.getString("LobbyId");
          gameServer.createLobby(lobbyId);
          sendLobbyList(conn);
          break;
        case "JOIN_LOBBY":
          lobbyId = jsonMessage.getString("LobbyId");
          Player newPlayer = new Player(playerId, "Player " + (gameServer.getPlayerCount() + 1));
          GameSession session = gameServer.joinLobby(lobbyId, newPlayer);
          if (session != null) {
            conn.setAttachment(session);
            sendPlayerInfo(conn, newPlayer);
            sendGameStateToAllPlayers(session);
          } else {
            sendError(conn, "Unable to join lobby");
          }
          break;
        case "GET_LOBBIES":
          sendLobbyList(conn);
          break;
        case "BUY_VOWEL":
        case "SELECT_CONSONANT":
        case "SOLVE":
          handleGameAction(conn, action, jsonMessage);
          break;
      }
    } catch (JSONException e) {
      System.err.println("Error parsing message: " + e.getMessage());
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    System.err.println("Error occurred: " + ex.getMessage());
    if (conn != null) {
      conns.remove(conn);
    }
  }

  @Override
  public void onStart() {
    System.out.println("WebSocket server started successfully.");
  }

  private void handleGameAction(WebSocket conn, String action, JSONObject jsonMessage) throws JSONException {
    GameSession session = conn.getAttachment();
    if (session != null) {
      String playerId = conn.getRemoteSocketAddress().toString();
      switch (action) {
        case "BUY_VOWEL":
          session.buyVowel(playerId);
          break;
        case "SELECT_CONSONANT":
          session.selectConsonant(playerId, jsonMessage.getString("Guess"));
          break;
        case "SOLVE":
          session.attemptSolve(playerId, jsonMessage.getString("Guess"));
          break;
      }
      sendGameStateToAllPlayers(session);
    }
  }

  private void sendLobbyList(WebSocket conn) {
    JSONObject lobbyList = new JSONObject();
    lobbyList.put("Action", "LOBBY_LIST");
    lobbyList.put("Lobbies", gameServer.getAvailableLobbies());
    conn.send(lobbyList.toString());
  }

  private void sendPlayerInfo(WebSocket conn, Player player) {
    JSONObject playerInfo = new JSONObject();
    playerInfo.put("Action", "PLAYER_INFO");
    playerInfo.put("PlayerId", player.getPlayerId());
    playerInfo.put("PlayerName", player.getName());
    conn.send(playerInfo.toString());
  }

  private void sendError(WebSocket conn, String errorMessage) {
    JSONObject error = new JSONObject();
    error.put("Action", "ERROR");
    error.put("Message", errorMessage);
    conn.send(error.toString());
  }

  private void sendGameStateToAllPlayers(GameSession session) {
    for (Player player : session.getPlayers()) {
      WebSocket conn = findConnectionForPlayer(player.getPlayerId());
      if (conn != null) {
        sendGameStateToPlayer(conn, session);
      }
    }
  }

  private void sendGameStateToPlayer(WebSocket conn, GameSession session) {
    JSONObject gameState = new JSONObject();
    gameState.put("Action", "GAME_STATE");
    gameState.put("puzzle", session.getCurrentPuzzle().getDisplayedPuzzle());
    gameState.put("scores", session.getPlayers().stream().map(p -> new JSONObject()
      .put("name", p.getName())
      .put("score", p.getScore())
      .put("playerId", p.getPlayerId())).toArray());
    gameState.put("currentPlayer", session.getCurrentPlayer().getPlayerId());
    gameState.put("currentStake", session.getCurrentStake());
    gameState.put("round", session.getRound());
    gameState.put("gameStatus", session.getGameStatus());
    conn.send(gameState.toString());
  }

  private WebSocket findConnectionForPlayer(String playerId) {
    for (WebSocket conn : conns) {
      if (conn.getRemoteSocketAddress().toString().equals(playerId)) {
        return conn;
      }
    }
    
return null;

  }

}