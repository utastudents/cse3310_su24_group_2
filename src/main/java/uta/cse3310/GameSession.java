package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private List<Player> players;
    private boolean isActive;

    public GameSession() {
        this.players = new ArrayList<>();
        this.isActive = false;
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
        if (players.size() > 1) {
            isActive = true;
            // will add more game start logic
        }
    }

    public void endGame() {
        isActive = false;
            // will add more game ending logic
    }
}

