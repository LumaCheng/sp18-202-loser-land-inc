package com.loserland.context;

import org.apache.commons.lang3.SerializationUtils;

public class GameCheckPoint{
    private GameState state;

    public GameCheckPoint(GameState state) {
        this.state = SerializationUtils.clone(state);
    }

    public GameState getState() {
        return state;
    }

}
