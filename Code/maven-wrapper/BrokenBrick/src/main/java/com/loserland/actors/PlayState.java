package com.loserland.actors;
import greenfoot.*;
import greenfoot.GreenfootImage;
import com.loserland.configs.*;
import com.loserland.context.GameContext;

public class PlayState extends Actor implements State  {

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public void PlayState(){

    }

    public void act() {


    }

    public GreenfootImage getImage(){

        return new GreenfootImage(config.get(GameContext.PLAYER_PAUSE_IMG));
    }

    public void doAction(Musicplayer musicplayer){
        musicplayer.setState(this);
    }
}
