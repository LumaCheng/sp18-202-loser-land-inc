package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.Greenfoot;

public class PowerGenerator {

    private static Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    public static double powerUpRate = Double.parseDouble(config.get(GameContext.BALL_POWER_RATE));

    public static void setPowerUpRate(double rate) {
        powerUpRate = rate;
    }

    public static void generatePowerSquare(BasicBall ball, Brick brick) {
        int hitNumber = Greenfoot.getRandomNumber((int)(PowerSquareFactory.getNumberOfPowers()/powerUpRate));
        if(hitNumber < PowerSquareFactory.getNumberOfPowers() - 1) {
            PowerSquareFactory.PowerType type = PowerSquareFactory.PowerType.values()[hitNumber];
            PowerSquare powerSquare;
            if(type == ball.getCurrentPower())
                powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.NORMAL);
            else
                powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.FIRE_BALL);
            if(powerSquare != null) {
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
        }
    }
}
