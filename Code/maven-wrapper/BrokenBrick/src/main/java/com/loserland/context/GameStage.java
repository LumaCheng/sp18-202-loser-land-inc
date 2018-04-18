package com.loserland.context;

import com.loserland.actors.Brick;
import greenfoot.Actor;

import java.util.ArrayList;
import java.util.List;

public class GameStage {
    private List<GameBrick> bricks;

    public GameStage() {
        bricks = new ArrayList<>();
    }

    public List<GameBrick> getBricks() {
        return bricks;
    }

    public void addBrick(GameBrick brick){
        bricks.add(brick);
    }
    public void setBricks(List<GameBrick> bricks) {
        this.bricks = bricks;
    }

    @Override
    public String toString() {
        String string = "";
        for(GameBrick brick: bricks){
            string +=  "(" + brick.getX() + "," + brick.getY() + ")\n";
        }
        return string;
    }
}
