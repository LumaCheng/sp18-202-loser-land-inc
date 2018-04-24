package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.worlds.MainWorld;
import greenfoot.Greenfoot;

import java.util.List;

public class MultiballPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    List<String> multiballImg = config.getList(GameContext.MULTIBALL_IMAGE);

    @Override
    void launchPower() {
        int size = multiballImg.size();
        BasicBall origBall = getWorld().getObjects(BasicBall.class).get(0);
        for(int i = 0; i < size; i++) {
            BasicBall ball;
            if(i == 0) {
                ball = origBall;
            } else {
                ball = new BasicBall();
            }
            ball.setImage(multiballImg.get(i));
            int randomX = Greenfoot.getRandomNumber(10) - 5;
            int randomY = Greenfoot.getRandomNumber(10) - 5;
            int newX = origBall.getX()+randomX;
            int newY = origBall.getY()+randomY;

            if(i != 0) {
                ball.changeX = origBall.changeX;
                ball.changeY = origBall.changeY;
                ball.setOnPaddle(origBall.onPaddle);
            }

            MultiballDecorator multiballDecorator = new MultiballDecorator();
            multiballDecorator.assemble(ball);

            if(i != 0) {
                getWorld().addObject(ball, newX, newY);
                if(!ball.onPaddle)
                    ball.launch(Greenfoot.getRandomNumber(1000)-500,
                                Greenfoot.getRandomNumber(1000)-500);
            }
        }
    }
}
