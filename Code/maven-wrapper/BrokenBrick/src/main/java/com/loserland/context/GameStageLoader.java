package com.loserland.context;

import com.loserland.configs.JsonDeserializer;

import java.io.IOException;


public class GameStageLoader {

    private static GameStageLoader instance = null;
    private GameStageLoader() { }

    public static GameStageLoader getInstance(){
        if (instance == null){
            synchronized (GameStageLoader.class){
                if (instance == null){
                    instance = new GameStageLoader();
                }
            }
        }
        return instance;
    }

    public GameStage load() {
        GameStage stage = null;
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        try {
            stage = jsonDeserializer.toObject(GameContext.GAME_STAGE_CONFIG_FILENAME, GameStage.class);
        } catch (IOException e) {
            e.printStackTrace();
            stage = GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.EASY);
        }
        return stage;
    }
}
