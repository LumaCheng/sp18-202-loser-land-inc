package com.loserland.actors;
import greenfoot.*;
import greenfoot.GreenfootImage;
import com.loserland.configs.*;
import com.loserland.context.GameContext;

public class PauseState extends Actor implements State {

    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public void PauseState(){

    }

    public void act() {


    }
    public GreenfootImage getImage(){

        return new GreenfootImage(config.get(GameContext.PLAYER_PLAY_IMG));
    }

    public void doAction(Musicplayer musicplayer) {
        musicplayer.setState(this);
    }
}