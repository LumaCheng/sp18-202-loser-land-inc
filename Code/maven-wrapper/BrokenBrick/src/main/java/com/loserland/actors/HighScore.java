package com.loserland.actors;

import greenfoot.Actor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HighScore extends Actor {
    private int score;
    private String time;


    public HighScore(int score){
        this.score = score;
        this.time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

    }

    public HighScore(int score, String time){
        this.score = score;
        this.time = time;
    }

    public int getScore() {
        return this.score;
    }

    public String gettime() {
        return this.time;
    }

    public String toString(){
        return ( score + "  "+ time);
    }
}
