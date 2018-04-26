package com.loserland.actors;

import com.loserland.configs.Config;
import com.loserland.configs.ConfigFactory;
import com.loserland.context.GameContext;
import com.loserland.utils.GifImage;
import greenfoot.Actor;

public class Explosion extends Actor {
    GifImage gifImage;
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);

    public Explosion() {
        gifImage = new GifImage(config.get(GameContext.EXPLOSION_IMG), 30);
    }

    @Override
    public void act() {
        if(gifImage.isRepeated()) {
            getWorld().removeObject(this);
        } else {
            setImage(gifImage.getCurrentImage());
        }
    }
}
