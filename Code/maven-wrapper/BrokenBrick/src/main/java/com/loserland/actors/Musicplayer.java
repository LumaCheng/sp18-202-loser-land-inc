package com.loserland.actors;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class musicplayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Musicplayer extends Actor
{
    
    /**
     * Act - do whatever the musicplayer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //GreenfootSound backgroundMusic = new GreenfootSound("theme.wav");
    
    private GreenfootImage button_1 = new GreenfootImage("play.png");
    private GreenfootImage button_2 = new GreenfootImage("pause.png");
 
    private boolean pause;
    // width of the score board
    private int WIDTH = 30;  
    // height of the score board
    private int HEIGHT = 30;  

    public Musicplayer() {
        button_1.scale(WIDTH,HEIGHT);
        button_2.scale(WIDTH, HEIGHT);
        // display on screen
        setImage(button_1);

        pause = false;
    }
 
    public void act() {        
        if (Greenfoot.mouseClicked(this)) {    
            if(pause){
                setImage(button_1); 
                pause = false; 
            }
            else{
                setImage(button_2); 
                pause = true; 
            }
            
        }    
    }
    
}
