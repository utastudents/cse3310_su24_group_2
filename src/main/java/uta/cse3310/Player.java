//Player.java represents a player; their score and status online or not
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
        this.isOnline = false;
    }

    //With score
    public Player(String playerId, String name, int score) {
        this.playerId = playerId;
        this.name = name;
        this.score = score;
        this.isOnline = false;
        
    }

    public Player(String string) {
        //TODO Auto-generated constructor stub
    }

    //Get and set player id
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    //Getter and setter for name

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    //Getter and setter for score
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //Getter and setter for online status 
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        this.isOnline = online;
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


}

