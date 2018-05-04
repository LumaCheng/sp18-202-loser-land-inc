package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;

import java.util.List;

public class SpeedUpPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public SpeedUpPower() {
        setImage(config.get(GameContext.SPEEDUP_PWR_IMG));
    }

    @Override
    void launchPower() {
        List<BasicBall> ballList = getWorld().getObjects(BasicBall.class);
        for(BasicBall ball : ballList) {
            ball.adjustSpeed(0.3);
        }
    }
}
