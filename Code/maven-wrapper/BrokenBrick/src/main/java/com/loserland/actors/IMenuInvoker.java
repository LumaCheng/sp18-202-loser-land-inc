package com.loserland.actors;

public interface IMenuInvoker
{
    void setClickCommand( ICommand c ) ;
    void setHoverCommand( ICommand c ) ;
    void setPressCommand( ICommand c ) ;
    void click() ;
    void press() ;
    void hover() ;
}