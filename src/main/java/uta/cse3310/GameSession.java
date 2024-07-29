package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
            if (players.size() >= 2 && players.size() <= 4) {
                startGame();
            }
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
        if (players.size() < 2) {
            endGame();
        }
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
        notifyPlayers();
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
        // This method will be called by WebSocketHandler to send updates to players
    }

    public void buyVowel(String playerId) {
        Player player = findPlayerById(playerId);
        if (player != null && player == getCurrentPlayer()) {
            if (player.getScore() >= 250) {
                player.updateScore(-250);
                // Logic to reveal a vowel
                nextTurn();
            }
        }
    }

    public void selectConsonant(String playerId, String consonant) {
        Player player = findPlayerById(playerId);
        if (player != null && player == getCurrentPlayer()) {
            boolean revealed = currentPuzzle.revealLetter(consonant.charAt(0));
            if (revealed) {
                // Update score based on number of revealed letters
                // Keep turn
            } else {
                nextTurn();
            }
        }
    }

    public void attemptSolve(String playerId, String guess) {
        Player player = findPlayerById(playerId);
        if (player != null && player == getCurrentPlayer()) {
            if (currentPuzzle.checkSolved(Arrays.asList(guess.split("\\s+")))) {
                player.updateScore(1000); // Bonus for solving
                endRound();
            } else {
                nextTurn();
            }
        }
    }

    private Player findPlayerById(String playerId) {
        return players.stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return players;
    }
}
