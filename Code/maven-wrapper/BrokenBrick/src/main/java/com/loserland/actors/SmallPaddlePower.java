package com.loserland.actors;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class com.loserland.actors.LargePaddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SmallPaddlePower extends PowerSquare
{
    @Override
    void launchPower() {
        paddle.shrink();
    }
}
