package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.Actor;

import java.util.List;

public class FireBallPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public FireBallPower() {
        setImage(config.get(GameContext.FIRE_PWR_IMG));
    }

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
