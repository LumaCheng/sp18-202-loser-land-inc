package com.loserland.actors;

import java.util.List;

public class PyroblastPower extends PowerSquare {
    @Override
    void launchPower() {
        PyroblastDecorator pyroblast = new PyroblastDecorator();
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(int i = 0; i < ballList.size(); i++) {
            BasicBall ball = ballList.get(i);
            if(ball.getWorld() != null) {
                if(i == 0)
                    pyroblast.assemble(ball);
                else
                    getWorld().removeObject(ball);
            }
        }
    }
}
