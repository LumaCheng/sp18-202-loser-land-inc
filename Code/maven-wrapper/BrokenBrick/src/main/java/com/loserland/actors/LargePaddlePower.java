package com.loserland.actors;


import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class com.loserland.actors.LargePaddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargePaddlePower extends PowerSquare
{
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public LargePaddlePower() {
        setImage(config.get(GameContext.LARGE_PAD_PWR_IMG));
    }

    @Override
    void launchPower() {
        paddle.enlarge();
    }
}
