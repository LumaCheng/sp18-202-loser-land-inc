package com.loserland.context;

import com.loserland.actors.Brick;

import java.io.Serializable;

public class GameBrick implements Serializable, Restorable<Brick> {
    private int type;
    private int x;
    private int y;

    public GameBrick() {
    }

    public GameBrick(int type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getType() {
        return type;
    }
//
//    public void setType(int type) {
//        this.type = type;
//    }

    @Override
    public int getX() {
        return x;
    }

//    public void setX(int x) {
//        this.x = x;
//    }

    @Override
    public int getY() {
        return y;
    }

//    public void setY(int y) {
//        this.y = y;
//    }

    @Override
    public Brick restore() {
        return new Brick(type);
    }
}
