

package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession {
    private List<Player> players;
    private boolean isActive;
    private WordPuzzle currentPuzzle;
    private int currentTurnIndex;
    private int round;
    private int currentStake;
    private static final int VOWEL_COST = 250;
    private static final int[] STAKES = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 0, -1}; 

    public GameSession() {
        this.players = new ArrayList<>();
        this.isActive = false;
        this.currentPuzzle = new WordPuzzle();
        this.currentTurnIndex = 0;
        this.round = 1;
    }

    public void addPlayer(Player player) {
        if (!isActive && players.size() < 4) {
            players.add(player);
            if (players.size() >= 2) {
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
        if (!isActive && players.size() >= 2) {
            isActive = true;
            startRound();
        }
    }

    private void startRound() {
        currentPuzzle.generatePuzzle(2);
        currentTurnIndex = 0;
        selectStake();
        notifyPlayers();
    }

    private void selectStake() {
        Random random = new Random();
        currentStake = STAKES[random.nextInt(STAKES.length)];
    }

    public void buyVowel(String playerId) {
        Player player = getCurrentPlayer();
        if (player.getPlayerId().equals(playerId) && player.getScore() >= VOWEL_COST) {
            player.updateScore(-VOWEL_COST);
            // Logic to let the player choose a vowel and reveal it
            String vowels = "aeiou";
            char vowel = vowels.charAt(new Random().nextInt(vowels.length()));
            if (currentPuzzle.revealLetter(vowel)) {

            } else {
                nextTurn();
            }
        }
    }

    public void selectConsonant(String playerId, String consonant) {
        Player player = getCurrentPlayer();
        if (player.getPlayerId().equals(playerId)) {
            if (currentPuzzle.revealLetter(consonant.charAt(0))) {
                int occurrences = countOccurrences(currentPuzzle.getDisplayedPuzzle(), consonant.charAt(0));
                player.updateScore(currentStake * occurrences);
                // Keep the turn
            } else {
                nextTurn();
            }
        }
    }

    public void attemptSolve(String playerId, String guess) {
        Player player = getCurrentPlayer();
        if (player.getPlayerId().equals(playerId)) {
            if (currentPuzzle.checkSolved(guess)) {
                player.updateScore(1000); // Bonus for solving
                endRound();
            } else {
                nextTurn();
            }
        }
    }

    private void nextTurn() {
        currentTurnIndex = (currentTurnIndex + 1) % players.size();
        selectStake();
        notifyPlayers();
    }

    private void endRound() {
        round++;
        if (round > 3) {
            endGame();
        } else {
            startRound();
        }
    }

    private void endGame() {
        isActive = false;
        Player winner = determineWinner();
        // Notify players of the winner
    }

    private Player determineWinner() {
        return players.stream().max((p1, p2) -> p1.getScore() - p2.getScore()).orElse(null);
    }

    private int countOccurrences(String str, char ch) {
        return (int) str.chars().filter(c -> c == ch).count();
    }

    private void notifyPlayers() {
        // This  will be called by WebSocketHandler(websocket) to send updates to players
    }

    public Player getCurrentPlayer() {
        return players.get(currentTurnIndex);
    }

    public WordPuzzle getCurrentPuzzle() {
        return currentPuzzle;
    }

    public int getCurrentStake() {
        return currentStake;
    }

    public int getRound() {
        return round;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getGameStatus() {
        if (!isActive) {
            return players.size() < 2 ? "Waiting for more players..." : "Game will start soon...";
        } else {
            return "Game in progress";
        }
    }
}
