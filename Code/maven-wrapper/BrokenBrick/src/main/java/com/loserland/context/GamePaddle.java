package com.loserland.context;

import com.loserland.actors.Paddle;

import java.io.Serializable;

//public class GamePaddle implements Serializable{
public class GamePaddle implements Serializable, Restorable<Paddle> {

    private int paddleWidth;
    private int x;
    private int y;

    public GamePaddle(){
        this(100);
        this.x = 350;
        this.y = 494;
    }

    public GamePaddle(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    @Override
    public Paddle restore() {
        Paddle paddle = new Paddle();
        paddle.setWidth(paddleWidth);
        return paddle;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }
}
