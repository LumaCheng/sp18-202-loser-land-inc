package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Live here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lives extends Actor
{
    // Call Greenfoot image command
    private GreenfootImage image;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public Lives()
    {
        // sets new image as life "bar". Total 3.
        image = new GreenfootImage(config.get(GameContext.currentPaddleImg));
        // shrink to scale
        image.scale(43,13);
        // add image to world
        setImage(image);
    }     
}
