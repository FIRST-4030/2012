/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Ingyram
 */
public class LEDRing extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Relay light=new Relay(RobotMap.LED_RING);
    public boolean on = false;
    
    public void on(){
        light.set(Relay.Value.kForward);
    }
    public void off(){
        light.set(Relay.Value.kReverse);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
