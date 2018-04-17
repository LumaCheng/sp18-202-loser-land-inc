package com.loserland.actors;

import greenfoot.Actor;

import java.util.List;

public class FireBallPower extends PowerSquare {
    @Override
    void launchPower() {
        FireBallDecorator fireball = new FireBallDecorator();
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(BasicBall ball : ballList) {
            fireball.assemble(ball);
        }
    }
}
