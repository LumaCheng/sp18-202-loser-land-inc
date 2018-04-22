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

    GreenfootSound backgroundMusic;
    private GreenfootImage button = new GreenfootImage("pause.png");

    // width of the score board
    private int WIDTH = 30;  
    // height of the score board
    private int HEIGHT = 30;
    private State state;


    public Musicplayer(GreenfootSound bg) {
        backgroundMusic = bg;
        backgroundMusic.playLoop();
        button.scale(WIDTH,HEIGHT);
        // display on screen
        setImage(button);
        state = null;
    }
 
    public void act() {        

    }

    public void change(){
        GreenfootImage button = state.getImage();
        button.scale(WIDTH, HEIGHT);
        setImage(button);
        if(backgroundMusic.isPlaying()){
            backgroundMusic.pause();
        }
        else{
            backgroundMusic.playLoop();
        }
    }


    public void setState(State state){
        this.state = state;
        change();
    }

    public State getState(){
        return state;
    }
}

