package com.loserland.actors;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CoverPage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MenuButton extends Actor implements IMenuInvoker
{
    private ICommand clickCmd ;
    private String normalImg;
    private String hoverImg;
    private String pressImg;

    public MenuButton(String normalImg, String hoverImg, String pressImg){
        this.normalImg = normalImg;
        this.hoverImg = hoverImg;
        this.pressImg = pressImg;
        setImage(this.normalImg);
    }

    public MenuButton(String normalImg){
        this.normalImg = normalImg;
        this.hoverImg = normalImg;
        this.pressImg = normalImg;
        setImage(this.normalImg);
    }

    public void resetImage(){
        this.setImage(normalImg);
    }


    @Override
    public void setCommand(ICommand c) {
        clickCmd = c;
    }

    @Override
    public void click() {
        clickCmd.execute();
    }

    @Override
    public void press() {
        this.setImage(pressImg);
    }

    @Override
    public void hover() {
        this.setImage(hoverImg);
    }
}