/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadVin;

/**
 *
 * @author prog
 */
public class Vin extends Subsystem {

    private AnalogChannel vin;

    public Vin() {
        vin = new AnalogChannel(RobotMap.VIN);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadVin());
    }

    public double readVoltage() {
        double voltage = vin.getAverageVoltage();
        SmartDashboard.putDouble("Vin", voltage);
        return voltage;
    }
}
