package com.loserland.actors;

public class Command implements ICommand
{
    IReceiver target ;

    public void execute()
    {
        target.doAction() ;
    }

    public void setReceiver( IReceiver t )
    {
        target = t ;
    }
}
