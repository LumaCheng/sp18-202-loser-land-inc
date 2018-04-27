package com.loserland.context;

import com.loserland.actors.LevelLabel;

public class GameLevel implements Restorable<LevelLabel>{

    private int level;
    private int x;
    private int y;

    public GameLevel() {
        level = 1;
        x = 575;
        y = 511;
    }

    public GameLevel(int level, int x, int y) {
        this.level = level;
        this.x = x;
        this.y = y;
    }

    @Override
    public LevelLabel restore() {
        return new LevelLabel(level);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
