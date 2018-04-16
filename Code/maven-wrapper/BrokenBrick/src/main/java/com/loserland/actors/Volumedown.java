package com.loserland.actors;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class volumedown here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Volumedown extends Actor
{
    private GreenfootImage button = new GreenfootImage("volumedown.png");
    private int WIDTH = 10;  
    // height of the score board
    private int HEIGHT = 10; 
    private int volume = 65;
    private long lastAdded;
   
    public void Volumedown() 
    {
        button.scale(WIDTH,HEIGHT);
        // display on screen
        setImage(button);
        // Add your action code here.
    }    
    
    public void act(){   
        if (Greenfoot.mouseClicked(this)){
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
