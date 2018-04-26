package com.loserland.controller;

import com.loserland.controller.ControllerEvent.CommandType;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

/**
 * Game Mouse Controller
 *
 * Wrap Greefoot mouse to convert mouse event to controller command
 *
 * TODO: Mapping different user config
 */
public class MouseController extends Controller {

    public MouseController(World world) {
        super(world);
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
    public void polling() {

        MouseInfo mouse = Greenfoot.getMouseInfo();

        // Mouse is outside of World, ignore its operation
        if(mouse == null) {
            return;
        }

        if(Greenfoot.mouseClicked(null)) {
            notifyObserver(
                new ControllerEvent(mouse.getX(), mouse.getY(), CommandType.LAUNCH));
        }

        if(Greenfoot.mouseMoved(null)) {
            notifyObserver(
                new ControllerEvent(mouse.getX(), mouse.getY(), CommandType.MOVE));
        }

    }

}
