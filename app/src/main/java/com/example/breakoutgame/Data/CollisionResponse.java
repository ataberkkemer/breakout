package com.example.breakoutgame.Data;

public class CollisionResponse {
    private int score;
    private int lives;

    public CollisionResponse(int score, int lives) {
        this.score = score;
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
