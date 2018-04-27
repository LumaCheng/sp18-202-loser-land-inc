package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends Actor
{
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public GameOver() {
        setImage(config.get(GameContext.GAMEOVER_IMG));
    }
    /**
     * Blitzcrank, champion of League of Legends. Riot Inc. copyright
     */
    public void act() 
    {
        // Add your action code here.
    }    
}
