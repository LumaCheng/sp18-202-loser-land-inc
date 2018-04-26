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
        String ballUiImage = ballUIChange(score);
        GameContext.currentBallImg = ballUiImage;
        //ballList = mainWorld.getObjects(BasicBall.class);
        //this.setBallImage(ballList);
    }

    public String ballUIChange(int score){
        String returnString = GameContext.BALL_IMAGE;
        if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_6))){
            returnString = GameContext.BIRD_IMAGE;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_5))){
            returnString = GameContext.SHIELD_IMAGE;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_4))){
            returnString = GameContext.PIZZA_IMAGE;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_3))){
            returnString = GameContext.POKEMON_IMAGE;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_2))){
            returnString = GameContext.SOCCER_IMAGE;
        }
        else if(score >= Integer.parseInt(config.get(GameContext.SCORE_LEVEL_1))){
            returnString = GameContext.BASEBALL_IMAGE;
        }
        return returnString;
    }

    private void setBallImage(List<BasicBall> balls) {
        for (BasicBall ball : balls) {
            ball.setImage(config.get(GameContext.currentBallImg));
        }
    }
}
