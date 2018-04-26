package com.loserland.actors;

import greenfoot.Actor;

import java.util.List;

public class FireBallPower extends PowerSquare {
    @Override
    void launchPower() {
        FireBallDecorator fireball = new FireBallDecorator();
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(int i = 0; i < ballList.size(); i++) {
            BasicBall ball = ballList.get(i);
            if(ball.getWorld() != null) {
                if(i == 0)
                    fireball.assemble(ball);
                else
                    getWorld().removeObject(ball);
            }
        }
    }
}
