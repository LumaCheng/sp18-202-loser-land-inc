package com.loserland.actors;


import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.worlds.*;

public class ContextController implements IObserver {

    MainWorld mainWorld;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);


    public ContextController(){

    }

    public void setMainWorld(MainWorld world){
        this.mainWorld = world;
    }



    public void update(int score){
        GameContext.BallType ballUiImage = ballUIChange(score);
        GameContext.currentBallImg = ballUiImage;
        //ballList = mainWorld.getObjects(BasicBall.class);
        //this.setBallImage(ballList);
    }

    public GameContext.BallType ballUIChange(int score){
        GameContext.BallType returnString = GameContext.BallType.NORMAL;
        if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_6.getKey()))){
            returnString = GameContext.BallType.BIRD;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_5.getKey()))){
            returnString = GameContext.BallType.SHIELD;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_4.getKey()))){
            returnString = GameContext.BallType.PIZZA;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_3.getKey()))){
            returnString = GameContext.BallType.POKEMON;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_2.getKey()))){
            returnString = GameContext.BallType.SOCCER;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.ScoreLevel.SCORE_LEVEL_1.getKey()))){
            returnString = GameContext.BallType.BASEBALL;
        }
        return returnString;
    }
}
