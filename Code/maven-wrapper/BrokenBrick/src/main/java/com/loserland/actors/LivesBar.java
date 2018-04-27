package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.Actor;

import java.util.ArrayList;
import java.util.List;

public class LivesBar{

    List<Lives> bars;


    public LivesBar() {
        bars = new ArrayList<>();

        Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
        int size = config.get(Integer.class, GameContext.PLAYER_LIVES);
        for (int i = 0; i < size; i++){
            bars.add(new Lives());
        }
    }

    public List<Lives> getBars() {
        return bars;
    }

    public int getLives(){
        return bars.size();
    }

    public void remove(int count) {
        resetLives(bars.size() - count);
    }

    public void resetLives(int lives) {
        bars.clear();
        for (int i = 0; i < lives; i++) {
            bars.add(new Lives());
        }

    }
}
