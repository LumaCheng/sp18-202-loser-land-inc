package com.loserland.context;

import com.loserland.configs.ConfigFactory;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class GameState implements Serializable {

    private GameStage stage;
    private int level;
    private int score;

    private int lives;

    public GameState() {
        stage = new GameStage();
        score = 0;
        level = 1;
        lives = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME).get(Integer.class, GameContext.PLAYER_LIVES);
    }

    public GameStage getStage() {
        return stage;
    }

    public void setStage(GameStage stage) {
        this.stage = stage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void clear() {
        stage.clear();
    }

    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
