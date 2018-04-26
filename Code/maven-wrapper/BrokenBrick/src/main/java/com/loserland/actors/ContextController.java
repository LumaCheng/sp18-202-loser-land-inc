package com.loserland.actors;


import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.worlds.*;
import java.util.ArrayList;
import java.util.List;

public class ContextController implements ScoreObserver {

    MainWorld mainWorld;
    List ballList = new ArrayList();
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
        if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_6))){
            returnString = GameContext.BallType.BIRD;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_5))){
            returnString = GameContext.BallType.SHIELD;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_4))){
            returnString = GameContext.BallType.PIZZA;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_3))){
            returnString = GameContext.BallType.POKEMON;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_2))){
            returnString = GameContext.BallType.SOCCER;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_1))){
            returnString = GameContext.BallType.BASEBALL;
        }
        return returnString;
    }

    private void setBallImage(List<BasicBall> balls) {
        for (BasicBall ball : balls) {
            ball.setImage(config.get(GameContext.currentBallImg.getKey()));
        }
    }
}
