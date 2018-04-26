package com.loserland.actors;

public interface ICommand {

    void execute() ;
    void setReceiver( IReceiver target ) ;
}
