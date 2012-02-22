package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadHoodAngle;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HoodAngle extends Subsystem {

    AnalogChannel pot = new AnalogChannel(RobotMap.HOOD_POTENTIOMETER);
    // The pot is wired backwards, so this is useless
    //AnalogChannel vin = new AnalogChannel(RobotMap.HOOD_POT_VIN);

    public void initDefaultCommand() {
        setDefaultCommand(new ReadHoodAngle());
    }

    public double getPosition() {
        double vangle = pot.getAverageVoltage();
        SmartDashboard.putDouble("Hood Angle", vangle);
        return (vangle);
    }
}
