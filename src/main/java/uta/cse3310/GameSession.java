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
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

//We will add more methods as we go on
}

