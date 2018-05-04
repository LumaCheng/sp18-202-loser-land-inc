package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.utils.GifImage;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CoverPage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Anime extends Actor
{
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    /**
     * Sets cover page image. Does nothing
     */
    public GifImage animeGif = new GifImage(config.get(GameContext.GIFIMAGE_ANIME), 60);

    public void act()
    {
        setImage(animeGif.getCurrentImage());
        // Add your action code here.
    }
}
