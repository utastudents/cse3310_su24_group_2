//Scoreboard.java keeps track of player scores.
package uta.cse3310;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scoreboard {
    private List<Player> highScores;

    public Scoreboard() {
        highScores = new ArrayList<>();
    }

    public void updateScoreboard(Player player) {
        highScores.add(player);
        highScores.sort(Comparator.comparingInt(Player::getScore).reversed());
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }
    }

    public List<Player> getTopPlayers() {
        return new ArrayList<>(highScores);
    }
}
