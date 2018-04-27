package com.loserland.context;

import com.loserland.actors.Lives;

import java.io.Serializable;
import java.util.List;

public class GameLives implements Serializable {

    public int getLivesNum() {
        return livesNum;
    }

    private int livesNum;
    private int lives_x;

    public void setLivesNum(int livesNum) {
        this.livesNum = livesNum;
    }

    public int getLives_x() {
        return lives_x;
    }

    public void setLives_x(int lives_x) {
        this.lives_x = lives_x;
    }

    public int getLives_x_incremental() {
        return lives_x_incremental;
    }

    public void setLives_x_incremental(int lives_x_incremental) {
        this.lives_x_incremental = lives_x_incremental;
    }

    public int getLives_y() {
        return lives_y;
    }

    public void setLives_y(int lives_y) {
        this.lives_y = lives_y;
    }

    private int lives_x_incremental;
    private int lives_y;

    public GameLives(int lives) {
        this.livesNum = lives;
        lives_x = 23;
        lives_x_incremental = 50;
        lives_y = 510;
    }


}
