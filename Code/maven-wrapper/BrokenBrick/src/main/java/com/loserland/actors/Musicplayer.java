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
public class MusicPlayer extends Actor implements ScoreObserver
{

    private static MusicPlayer instance;

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    GreenfootSound backgroundMusic = new GreenfootSound(config.get(GameContext.GAME_BACKGROUND_MUSIC));
    private GreenfootImage button = new GreenfootImage(config.get(GameContext.PLAYER_PAUSE_IMG));
    private PlayState playState = new PlayState();
    private StopState stopState = new StopState();
//    int volume;

    private State currentState;


    private MusicPlayer() {
        currentState = playState;
    }

    public static MusicPlayer getInstance(){
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }
 
    public void act() {

    }


    public void update(int v){
        backgroundMusic.setVolume(v);
    }

    public boolean isPlaying(){
        return backgroundMusic.isPlaying();
    }


    public void setCurrentState(String string){
        if(string.equals("stop")){
            this.currentState = stopState;
            currentState.doAction();
        }
        if(string.equals("play")){
            this.currentState = playState;
            currentState.doAction();
        }
    }


    public void changeState(){
        if(currentState.equals(playState)){
            currentState = stopState;
            currentState.doAction();
        }
        else{
            currentState = playState;
            currentState.doAction();
        }
    }

    public State getCurrentState(){
        return currentState;
    }
}

