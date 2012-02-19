package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Loader extends Subsystem {

    Victor loader;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Loader() {
        loader = new Victor(RobotMap.MOTOR_LOADER);
    }

    public void run(double speed) {
        SmartDashboard.putDouble("Loader Command Speed", speed);
        loader.set(speed);
    }

    public void stop() {
        loader.stopMotor();
    }
}
