package com.loserland.actors;
import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import greenfoot.*;
import greenfoot.GreenfootImage;

public class StopState extends Actor implements IState  {

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    GreenfootImage currentImage = new GreenfootImage(config.get(GameContext.PLAYER_PLAY_IMG));
    private int WIDTH = config.get(Integer.class, GameContext.PLAYER_SIZE);
    private int HEIGHT = config.get(Integer.class, GameContext.PLAYER_SIZE);

    public StopState(){
        currentImage.scale(WIDTH, HEIGHT);
    }

    public void act() {


    }

    public GreenfootImage getImage(){
        return new GreenfootImage(config.get(GameContext.PLAYER_PAUSE_IMG));
    }

    public void doAction(){
        MusicPlayer musicPlayer = MusicPlayer.getInstance();
        musicPlayer.setImage(currentImage);
        musicPlayer.backgroundMusic.stop();
    }

    public String toString(){return "StopState";}
}
