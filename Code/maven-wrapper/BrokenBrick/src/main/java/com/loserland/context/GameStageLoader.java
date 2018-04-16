package com.loserland.context;

import com.loserland.actors.Brick;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.configs.JsonDeserializer;
import com.loserland.worlds.MyWorld;

import java.io.IOException;


public class GameStageLoader {
//    public static final GameStage.Stage DEFAULT_STAGE = GameStage.Stage.DEFAULT;

    //Configs
    private static ConfigFactory configFactory;
//    private static Config config;
    private static Config stageConfig;

    static {
        configFactory = ConfigFactory.getInstance();
//        config = configFactory.getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
        stageConfig = configFactory.getConfig(GameContext.GAME_STAGE_CONFIG_FILENAME);
    }


    MyWorld world;
    GameStage currentGameStage;


    public GameStageLoader(MyWorld world) {
        this.world = world;
    }

    public void load(GameStage stage){
        for (GameBrick brick: stage.getBricks()){
            addBricks(brick);
        }
    }

    public void load() throws Exception {
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        try {
            currentGameStage = jsonDeserializer.toObject(GameContext.GAME_STAGE_CONFIG_FILENAME, GameStage.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Game stage initialization failed.");
        }
        load(currentGameStage);
    }

    public void addBricks(GameBrick brick){
        world.addObject(new Brick(brick.getType()), brick.getX(), brick.getY());
    }
}
