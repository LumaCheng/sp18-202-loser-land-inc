package com.loserland.context;

import com.loserland.worlds.MyWorld;
import greenfoot.Actor;
import greenfoot.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameStageLoader {
//    public static final GameStage.Stage DEFAULT_STAGE = GameStage.Stage.DEFAULT;

    MyWorld world;
    GameStage currentGameStage;

    public GameStageLoader(MyWorld world) {
        this.world = world;
    }

    public void load(GameStage stage){
        for (Actor actor: stage.getActors()){
            world.addObject(actor, actor.getX(), actor.getY());
        }
    }

    public void load() {
//        MyWorld.stage_config.put("bricks", Arrays.asList("1", "2", "3"));
//        ArrayList<String> list = MyWorld.stage_config.get(ArrayList.class, "bricks");
//        System.out.println(list.toString());

        currentGameStage = new GameStage();



//        currentGameStage.addActor();
        load(currentGameStage);
    }
}
