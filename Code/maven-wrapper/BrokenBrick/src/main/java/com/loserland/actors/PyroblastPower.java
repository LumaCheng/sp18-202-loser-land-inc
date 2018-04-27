package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;

import java.util.List;

public class PyroblastPower extends PowerSquare {
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public PyroblastPower() {
        setImage(config.get(GameContext.PYROBLAST_PWR_IMG));
    }

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
