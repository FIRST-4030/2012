/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Ingyram
 */
public class ArmLock extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    Servo servo=new Servo(7);
    private boolean isSet=false;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void set(double angle){
        servo.set(angle);
    }
    public void toggle(){
        if(isSet){
            servo.set(0);
            
        }else{
            servo.set(.6);
        }
        isSet=!isSet;
    }
}
