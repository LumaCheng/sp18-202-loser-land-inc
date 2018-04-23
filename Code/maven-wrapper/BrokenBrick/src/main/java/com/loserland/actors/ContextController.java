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
        String returnString = "ball.image";
        if(score >= 250){
            returnString = "shieldball.image";
        }
        else if(score >= 200){
            returnString = "pokemonball.image";
        }
        else if(score >= 150){
            returnString = "pizzaball.image";
        }
        else if(score >= 100){
            returnString = "soccerball.image";
        }
        else if(score >= 50){
            returnString = "baseball.image";
        }
        return returnString;
    }

    private void setBallImage(List<BasicBall> balls) {
        for (BasicBall ball : balls) {
            ball.setImage(config.get(GameContext.currentBallImg));
        }
    }
}
