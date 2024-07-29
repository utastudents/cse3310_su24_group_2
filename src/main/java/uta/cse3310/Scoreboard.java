//Scoreboard.java keeps track of player scores.
package uta.cse3310;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Scoreboard {
    private Map<String, Integer> scores;

    public Scoreboard() {
        scores = new HashMap<>();
    }

    public void updateScore(String playerId, int score) {
        scores.put(playerId, score);
    }

    public List<Player> getTopPlayers() {
        return scores.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> new Player(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}

     

