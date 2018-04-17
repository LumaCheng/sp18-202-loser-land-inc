package com.loserland.controller;

/**
 * ControllerEvent
 *
 * Controller event information
 */
public class ControllerEvent {

    /**
     *
     */
    public enum CommandType {
        UNKNOW,
        MOVE,
        LAUNCH,  // shoot ball
        PAUSE,
        EXIT
    }

    public CommandType type;
    public int x;
    public int y;

    public ControllerEvent() {
        this(0, 0, CommandType.UNKNOW);
    }

    /**
     *
     * @param x     x location
     * @param y     y location
     * @param type  command type
     */
    public ControllerEvent(int x, int y, CommandType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

}
