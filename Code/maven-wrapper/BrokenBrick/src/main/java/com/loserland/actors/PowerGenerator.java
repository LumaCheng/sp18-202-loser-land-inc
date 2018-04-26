package com.loserland.actors;

import greenfoot.Greenfoot;

public class PowerGenerator {

    public static double powerUpRate;

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
                powerSquare = PowerSquareFactory.makePowerSquare(type);
            if(powerSquare != null) {
                ball.getWorld().addObject(powerSquare, brick.getX(), brick.getY());
                powerSquare.fall();
            }
        }
    }
}
