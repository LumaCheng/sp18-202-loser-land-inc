package com.loserland.controller;

import com.loserland.controller.ControllerEvent.CommandType;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.World;

public class KeyBoardController extends Controller {

    // TODO: define in config file
    private static final String KEY_UP = "up";
    private static final String KEY_DOWN = "down";
    private static final String KEY_LEFT = "left";
    private static final String KEY_RIGHT = "right";
    private static final String KEY_LAUNCH = "space";

    private static final String[] MAPPING_KEYS = {
        KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT, KEY_LAUNCH
    };

    private double sensitivity;
    private int move_unit_px;
    private int world_abs_x;
    private int world_abs_y;

    public KeyBoardController(World world) {
        super(world);

        // TODO: Read from config
        sensitivity = 5.0;
        move_unit_px = 3;

        world_abs_x = world.getHeight() / 2;
        world_abs_y = world.getWidth() / 2;

    }


    @Override
    public void notifyObserver(ControllerEvent event) {

        for(ControllerObserver observer : observers) {
            if(observer.isInWorld()) {
                observer.controllerEventReceived(event);
            }
        }
    }

    @Override
    public boolean clicked(Actor actor) {
        return false;
    }


    @Override
    public void polling() {

        String mapping = getKey();

        if(mapping == null) {
            return;
        }

        if(isMovingOperation(mapping)) {
            sendMovingCommand(mapping);
        }

        if(mapping.equals(KEY_LAUNCH)) {
            notifyObserver(
                new ControllerEvent(world_abs_x, world_abs_y, CommandType.LAUNCH));
        }
    }

    private void sendMovingCommand(String key) {
        ControllerEvent event = new ControllerEvent();

        event.type = CommandType.MOVE;

        switch (key) {
            case KEY_UP:
                world_abs_y -= ((int)(move_unit_px * sensitivity));
                break;
            case KEY_DOWN:
                world_abs_y += ((int)(move_unit_px * sensitivity));
                break;
            case KEY_LEFT:
                world_abs_x -= ((int)(move_unit_px * sensitivity));
                break;
            case KEY_RIGHT:
                world_abs_x += ((int)(move_unit_px * sensitivity));
                break;
        }

        world_abs_y = (world_abs_y < 0 ? 0 : world_abs_y);
        world_abs_y = (world_abs_y > world.getHeight() ? world.getHeight() : world_abs_y);

        world_abs_x = (world_abs_x < 0 ? 0 : world_abs_x);
        world_abs_x = (world_abs_x > world.getWidth() ? world.getWidth() : world_abs_x);

        event.x = world_abs_x;
        event.y = world_abs_y;

        //System.out.println("Move event x: " + event.x);
        //System.out.println("Move event y: " + event.y);
        notifyObserver(event);
    }

    private String getKey() {

        String key = Greenfoot.getKey();

        if(key != null) {
            return key;
        }

        // Greenfoot does not provide key detection
        // We need go through the key we interested
        for(String map : MAPPING_KEYS) {
            if(Greenfoot.isKeyDown(map)) {
                return map;
            }
        }

        return null;
    }

    private boolean isMovingOperation(String key) {
        return key.equals(KEY_UP) || key.equals(KEY_DOWN) ||
            key.equals(KEY_LEFT) || key.equals(KEY_RIGHT);
    }

}
