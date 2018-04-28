package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.BallPool;
import com.loserland.context.GameContext;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.*;

public class MultiballPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    List<String> multiballImg = config.getList(GameContext.MULTIBALL_IMAGE);
    int number = Integer.parseInt(config.get(GameContext.MULTIBALL_NUMBER));
    Random rand = new Random();
    Set<Integer> usedImg = new HashSet<>();
    GreenfootImage currentImg;

    public MultiballPower() {
        setImage(config.get(GameContext.MULTIBALL_PWR_IMG));
    }

    @Override
    void launchPower() {
        usedImg.clear();
        int size = multiballImg.size();
        number = Math.min(size, number);
        BasicBall origBall = getWorld().getObjects(BasicBall.class).get(0);
        List<BasicBall> newBalls = BallPool.getInstance().fetch(number - 1);

        for(int i = 0; i < newBalls.size() + 1; i++) {
            BasicBall ball;
            if(i == 0) {
                ball = origBall;
            } else {
                ball = newBalls.get(i - 1);
                if (ball == null) return;
            }
            int  n = rand.nextInt(size-1);
            while(usedImg.contains(n)){
                n = rand.nextInt(size-1);
            }
            usedImg.add(n);
            currentImg = new GreenfootImage(multiballImg.get(n));
            currentImg.scale(30, 30);
            ball.setImage(currentImg);
            int randomX = Greenfoot.getRandomNumber(100) - 50;
            int randomY = Greenfoot.getRandomNumber(100) - 50;
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
                    ball.launch(Greenfoot.getRandomNumber(2000)-1000,
                                Greenfoot.getRandomNumber(2000)-1000);
            }
        }
    }
}
