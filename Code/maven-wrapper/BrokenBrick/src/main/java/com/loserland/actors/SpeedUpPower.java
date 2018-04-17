package com.loserland.actors;

import java.util.List;

public class SpeedUpPower extends PowerSquare {
    @Override
    void launchPower() {
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(BasicBall ball : ballList) {
            ball.adjustSpeed(0.5);
        }
    }
}
