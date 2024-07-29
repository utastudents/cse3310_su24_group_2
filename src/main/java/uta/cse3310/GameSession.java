// GameSession manags a single game; players, rounds
package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession {
    private List<Player> players;
    private int currentPlayerIndex;
    private int rounds;
    private WordPuzzle wordPuzzle;

    public GameSession(int numberOfPlayers) {
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.rounds = 0;
        this.wordPuzzle = new WordPuzzle();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }
    }

    public void addPlayer(Player player) {
        if (players.size() <= 4) {
            players.add(player);
        } else {
            throw new IllegalStateException("Maximum number of players reached.");
        }
    }

    public void startGame() {
        // Initialize game logic
        wordPuzzle.selectWords();
    }

    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        // Notify players of the next turn
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void solvePuzzle(String attempt) {
        if (wordPuzzle.isCorrect(attempt)) {
            // Handle correct attempt
        } else {
            // Handle incorrect attempt
        }
    }

    public void endRound() {
        rounds++;
        if (rounds >= 3) {
            // End game logic
        } else {
            // Start new round
        }
    }
}
