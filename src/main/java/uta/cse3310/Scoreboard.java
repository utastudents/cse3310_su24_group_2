package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private List<Player> highScores;

    public Scoreboard() {
        highScores = new ArrayList<>();
    }

    public void updateScoreboard(Player player) {
        // This should handle high scores and update to players
    }

    public List<Player> getTopPlayers() {
        return highScores;
    }
}

