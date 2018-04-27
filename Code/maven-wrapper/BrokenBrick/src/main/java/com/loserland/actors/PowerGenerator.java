package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.Greenfoot;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PowerGenerator {

    private static Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    public static double powerUpRate = Double.parseDouble(config.get(GameContext.BALL_POWER_RATE));
    private static double goldenEggRate = Double.parseDouble(config.get(GameContext.GOLDEN_EGG_PWR_RATE));
    private static Set<String> powerSet = new HashSet<String>(config.getList(GameContext.POWER_LIST));

    private static boolean hasCustomized = false;
    private static PowerSquareFactory.PowerType customizedType;
    private static double customizedRate;

    public static void setPowerUpRate(double rate) {
        powerUpRate = rate;
    }

    public static void customizePower(PowerSquareFactory.PowerType type, double rate) {
        hasCustomized = true;
        customizedType = type;
        customizedRate = rate;
    }

    public static void reset() {
        hasCustomized = false;
    }

    public static void generatePowerSquare(BasicBall ball, Brick brick) {
        PowerSquare powerSquare;

        if(hasCustomized) {
            if(0 == Greenfoot.getRandomNumber((int) (1 / customizedRate))) {
                powerSquare = PowerSquareFactory.makePowerSquare(customizedType);
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
            return;
        }

        int hitNumber = 0;
        PowerSquareFactory.PowerType type = null;

        // Golden egg power
        hitNumber = Greenfoot.getRandomNumber((int) (1 / goldenEggRate));
        if(hitNumber == 1) {
            powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.GOLDEN_EGG);
            if (powerSquare != null) {
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
                return;
            }
        }

        do {
            hitNumber = Greenfoot.getRandomNumber((int) (PowerSquareFactory.getNumberOfPowers() / powerUpRate));
            if (hitNumber < PowerSquareFactory.getNumberOfPowers() - 2)
                type = PowerSquareFactory.PowerType.values()[hitNumber];
        } while (type != null && !powerSet.contains(type.toString()));

        if (hitNumber < PowerSquareFactory.getNumberOfPowers() - 2) {
            if (type == ball.getCurrentPower() && powerSet.contains("NORMAL"))
                powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.NORMAL);
            else
                powerSquare = PowerSquareFactory.makePowerSquare(type);
            if (powerSquare != null) {
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
        }
    }
}
