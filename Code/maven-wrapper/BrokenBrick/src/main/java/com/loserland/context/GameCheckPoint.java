package com.loserland.context;

public class GameCheckPoint implements Cloneable {
    private GameState state;

    public GameCheckPoint(GameState state) {
        this.state = state;
    }

    public GameState getState() {
        return state;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
