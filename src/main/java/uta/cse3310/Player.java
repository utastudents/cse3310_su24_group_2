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

    public void joinGame(GameSession session) {
        session.addPlayer(this);
    }

    public void leaveGame(GameSession session) {
        session.removePlayer(this);
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
}


//This should handle players joining and leaving the game along with their current online status.
