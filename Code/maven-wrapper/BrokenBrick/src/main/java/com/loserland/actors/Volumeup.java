package com.loserland.actors;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class VOLUME here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Volumeup extends Actor
{
    /**
     * Act - do whatever the VOLUME wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    private GreenfootImage button = new GreenfootImage(config.get(GameContext.VOL_UP_IMG));
    private int WIDTH = 10;
    private int HEIGHT = 10;
    private int volume = Integer.parseInt(config.get(GameContext.VOLUME));
    long lastAdded;
    public void Volumeup() 
    {
        button.scale(WIDTH,HEIGHT);
        // display on screen
        setImage(button);
        // Add your action code here.
    }    
    
    public void act(){   
        if (Greenfoot.mouseClicked(this) ){
            setImage(button);       
            GreenfootImage show_volume = new GreenfootImage(""+volume, 20, Color.BLACK, new Color(0, 0, 0, 0));
            lastAdded = System.currentTimeMillis();
            setImage(show_volume);

        }
        
        if (System.currentTimeMillis() >= lastAdded + 500){
                setImage(button); 
        }

    }
    
       // updates the VOLUME display
    public void update(int v)
    {      
        volume = v;
    }
}
