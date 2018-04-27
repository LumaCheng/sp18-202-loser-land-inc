package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;

import java.util.List;

public class NormalPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public NormalPower() {
        setImage(config.get(GameContext.NORMAL_PWR_IMG));
    }

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
