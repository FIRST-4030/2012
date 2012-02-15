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

public class HoodAngle extends Subsystem {

    AnalogChannel angle = new AnalogChannel(RobotMap.HOOD_POTENTIOMETER);

    public void initDefaultCommand() {
        setDefaultCommand(new ReadHoodAngle());
    }

    public double getPosition() {
        return (angle.getAverageVoltage() / CommandBase.globalState.getVin());
    }
}
