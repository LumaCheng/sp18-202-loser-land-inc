package com.loserland.actors;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import com.loserland.configs.*;
import com.loserland.context.GameContext;
/**
 * Write a description of class volume here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Volumeup extends Actor
{

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    private GreenfootImage button = new GreenfootImage(config.get(GameContext.VOLUME_UP_IMG));
     private int WIDTH = config.get(Integer.class, GameContext.VOLUME_SIZE);
    // height of the score board
    private int HEIGHT = config.get(Integer.class, GameContext.VOLUME_SIZE);
     private int volume = config.get(Integer.class, GameContext.VOLUME_DEFAULT);
    long lastAdded;

    public Volumeup()
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
    
       // updates the volume display
    public void update(int v) 
    {      
        volume = v;
    }
}
