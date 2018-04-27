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
    private static Set<String> powerSet = new HashSet<String>(config.getList(GameContext.POWER_LIST));


    public static void setPowerUpRate(double rate) {
        powerUpRate = rate;
    }

    public static void generatePowerSquare(BasicBall ball, Brick brick) {
        int hitNumber = 0;
        PowerSquareFactory.PowerType type = null;
        do {
            hitNumber = Greenfoot.getRandomNumber((int) (PowerSquareFactory.getNumberOfPowers() / powerUpRate));
            if(hitNumber < PowerSquareFactory.getNumberOfPowers() - 1)
                type = PowerSquareFactory.PowerType.values()[hitNumber];
        } while(type != null && !powerSet.contains(type.toString()));

        if(hitNumber < PowerSquareFactory.getNumberOfPowers() - 1) {
            PowerSquare powerSquare;
            if(type == ball.getCurrentPower() && powerSet.contains("NORMAL"))
                powerSquare = PowerSquareFactory.makePowerSquare(PowerSquareFactory.PowerType.NORMAL);
            else
                powerSquare = PowerSquareFactory.makePowerSquare(type);
            if(powerSquare != null) {
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
        }
    }
}
