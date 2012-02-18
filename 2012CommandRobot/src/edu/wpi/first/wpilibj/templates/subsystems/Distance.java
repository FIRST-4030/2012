package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadDistance;

public class Distance extends Subsystem {

    private Ultrasonic range;

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadDistance());
    }

    public Distance() {
        range = new Ultrasonic(RobotMap.RANGEFINDER_A, RobotMap.RANGEFINDER_B);
        this.start();
    }
    
    public void start() {
        range.setEnabled(true);
        range.setAutomaticMode(true);
    }
    
    public void stop() {
        range.setEnabled(false);
    }
    
    public void ping() {
        range.ping();
    }

    public int readDistance() {
        int distance = -1;
        if (range.isRangeValid()) {
            distance = (int) range.getRangeInches();
        }
        SmartDashboard.putInt("Distance", distance);
        return distance;
    }
}
