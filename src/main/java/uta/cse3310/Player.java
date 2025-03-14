//Player.java reps a player; their score and status online or not
package uta.cse3310;
public class Player {
    private String playerId;
    private String name;
    private int score;
    private boolean isOnline;

    public Player(String playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.score = 0;
        this.isOnline = true;
    }

    public void updateScore(int points) {
        this.score += points;
    }

    public void setOnlineStatus(boolean status) {
        this.isOnline = status;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
