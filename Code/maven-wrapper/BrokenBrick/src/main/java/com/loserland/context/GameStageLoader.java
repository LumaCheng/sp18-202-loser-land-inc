package com.loserland.context;

import com.loserland.actors.Brick;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.configs.JsonDeserializer;
import com.loserland.worlds.MyWorld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameStageLoader {
    MyWorld world;
    GameStage currentGameStage;

    public GameStageLoader(MyWorld world) {
        this.world = world;
    }

    public void load(GameStage stage){
        List<Brick> bricks = new ArrayList<>();
        for (GameBrick gameBrick: stage.getBricks()){
            Brick brick = new Brick(gameBrick.getType());
            world.addObject(brick, gameBrick.getX(), gameBrick.getY());
            if (world.hasIntersectingActors(brick, Brick.class)){
                world.removeObject(brick);
            }
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
}
