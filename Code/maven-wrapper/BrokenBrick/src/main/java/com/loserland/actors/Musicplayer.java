package com.loserland.actors;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
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
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    private GreenfootImage button = new GreenfootImage(config.get(GameContext.PLAYER_PAUSE_IMG));
    private int WIDTH = config.get(Integer.class, GameContext.PLAYER_SIZE);
    private int HEIGHT = config.get(Integer.class, GameContext.PLAYER_SIZE);

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

    public void change() {
        if (this.state.toString().equals("StopState")) {
            backgroundMusic.stop();
        } else if (this.state.toString().equals("PauseState")) {
            GreenfootImage button = state.getImage();
            button.scale(WIDTH, HEIGHT);
            setImage(button);
            backgroundMusic.pause();
        } else if (this.state.toString().equals("PlayState")) {
            GreenfootImage button = state.getImage();
            button.scale(WIDTH, HEIGHT);
            setImage(button);
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

