package com.loserland.context;

import com.loserland.actors.Brick;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.configs.JsonDeserializer;
import com.loserland.worlds.MyWorld;

import java.io.IOException;


public class GameStageLoader {
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

    public void load() {
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        try {
            currentGameStage = jsonDeserializer.toObject(GameContext.GAME_STAGE_CONFIG_FILENAME, GameStage.class);
        } catch (IOException e) {
//            e.printStackTrace();
            currentGameStage = GameStageGenerator.getInstance().createStage(GameStageGenerator.Difficulty.HARD);
        }
        load(currentGameStage);
    }

    public void addBricks(GameBrick brick){
        world.addObject(new Brick(brick.getType()), brick.getX(), brick.getY());
    }
}
