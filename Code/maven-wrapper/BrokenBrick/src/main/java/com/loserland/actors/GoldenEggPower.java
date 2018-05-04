package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;

public class GoldenEggPower extends PowerSquare {

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    private double powerRate = Double.parseDouble(config.get(GameContext.GOLDEN_EGG_CUSTOM_PWR_RATE));

    public GoldenEggPower() {
        setImage(config.get(GameContext.GOLDEN_EGG_PWR_IMG));
    }

    @Override
    void launchPower() {
        PowerGenerator.customizePower(PowerSquareFactory.PowerType.MULTI_BALL, 1);
    }
}
