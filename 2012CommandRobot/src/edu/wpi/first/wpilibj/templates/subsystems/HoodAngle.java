/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadHoodAngle;

/**
 *
 * @author prog
 */
public class HoodAngle extends Subsystem
{
    AnalogChannel angle =new AnalogChannel(RobotMap.HOOD_POTENTIOMETER);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand()
    {
        setDefaultCommand(new ReadHoodAngle());
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public double getPosition()
    {
        return (angle.getVoltage()/CommandBase.globalState.getVin());
    }
}
