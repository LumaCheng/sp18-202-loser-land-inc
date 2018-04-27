package com.loserland.context;

import com.loserland.actors.ScoreBoard;

public class GameScore implements Restorable<ScoreBoard>{

    private int score;
    private int x;
    private int y;

    public GameScore() {
        score = 0;
        x = 658;
        y = 511;
    }

    public GameScore(int score, int x, int y) {
        this.score = score;
        this.x = x;
        this.y = y;
    }

    @Override
    public ScoreBoard restore() {
        return new ScoreBoard(score);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
