package com.loserland.actors;

import java.util.List;

public class NormalPower extends PowerSquare {
    @Override
    void launchPower() {
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(int i = 0; i < ballList.size(); i++) {
            BasicBall ball = ballList.get(i);
            ball.setDecorator(ball);
            if(i > 0) {
                getWorld().removeObject(ball);
            }
        }
    }
}
