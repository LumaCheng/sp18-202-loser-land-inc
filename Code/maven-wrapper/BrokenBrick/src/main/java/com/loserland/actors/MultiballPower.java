package com.loserland.actors;

import com.loserland.worlds.MainWorld;
import com.loserland.worlds.MyWorld;
import greenfoot.Greenfoot;

public class MultiballPower extends PowerSquare {
    @Override
    void launchPower() {
        int size = MainWorld.faceList.size();
        BasicBall origBall = getWorld().getObjects(BasicBall.class).get(0);
        for(int i = 0; i < size; i++) {
            BasicBall ball;
            if(i == 0) {
                ball = origBall;
            } else {
                ball = new BasicBall();
            }
            ball.setImage(MainWorld.faceList.get(i));
            int randomX = Greenfoot.getRandomNumber(4) - 2;
            int randomY = Greenfoot.getRandomNumber(5) - 2;
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
                    ball.launch(newX, newY);
            }
        }
    }
}
