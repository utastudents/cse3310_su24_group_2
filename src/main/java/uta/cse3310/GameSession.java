// GameSession manags a single game; players, rounds scoreboard
package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private List<Player> players;
    private boolean isActive;
    private WordPuzzle currentPuzzle;
    private int currentTurnIndex;
    private int round;
    private Scoreboard scoreboard;

    public GameSession() {
        this.players = new ArrayList<>();
        this.isActive = false;
        this.currentPuzzle = new WordPuzzle();
        this.currentTurnIndex = 0;
        this.round = 1;
        this.scoreboard = new Scoreboard();
    }

    public void addPlayer(Player player) {
        if (!isActive) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void startGame() {
        if (players.size() >= 2 && players.size() <= 4) {
            isActive = true;
            startRound();
        }
    }

    private void startRound() {
        currentPuzzle.generatePuzzle(2);
        currentTurnIndex = 0;
        // Notify all players about the new round
        notifyPlayers();
    }

    public void endRound() {
        round++;
        if (round > 3) {
            endGame();
        } else {
            startRound();
        }
    }

    public void endGame() {
        isActive = false;
        // Update scoreboard and notify players
    }

    public void nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % players.size();
        // Notify players about the next turn
    }

    public Player getCurrentPlayer() {
        return players.get(currentTurnIndex);
    }

    public WordPuzzle getCurrentPuzzle() {
        return currentPuzzle;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    private void notifyPlayers() {
        // Notify all players about the current puzzle and game state
        for (Player player : players) {
            player.updatePuzzle(currentPuzzle.getDisplayedPuzzle());
            player.updateScoreboard(scoreboard.getTopPlayers());
        }
    }
}

