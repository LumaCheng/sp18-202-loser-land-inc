package com.loserland.actors;

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CoverPage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MenuButton implements IMenuInvoker
{
    private ICommand pressCmd ;
    private ICommand clickCmd ;
    private ICommand hoverCmd ;


    @Override
    public void setClickCommand(ICommand c) {
        clickCmd = c;
    }

    @Override
    public void setHoverCommand(ICommand c) {
        hoverCmd = c;
    }

    @Override
    public void setPressCommand(ICommand c) {
        pressCmd = c;
    }

    @Override
    public void click() {
        clickCmd.execute();
    }

    @Override
    public void press() {
        pressCmd.execute();
    }

    @Override
    public void hover() {
        hoverCmd.execute();
    }
}