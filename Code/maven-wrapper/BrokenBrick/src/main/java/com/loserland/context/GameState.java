package com.loserland.context;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class GameState implements Serializable {

    private GameStage stage;

    public GameStage getStage() {
        return stage;
    }

    public void setStage(GameStage stage) {
        this.stage = stage;
    }
}
