package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadDistance;

public class Distance extends Subsystem {

    private AnalogChannel range;

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadDistance());
    }

    public Distance() {
        range = new AnalogChannel(RobotMap.RANGEFINDER);
    }
    
    public int readDistance() {
        double voltsPerInch = CommandBase.globalState.getVin() / 512.0;
        int distance = (int)(range.getAverageVoltage() * voltsPerInch);
        SmartDashboard.putInt("Distance", distance);
        return distance;
    }
}
