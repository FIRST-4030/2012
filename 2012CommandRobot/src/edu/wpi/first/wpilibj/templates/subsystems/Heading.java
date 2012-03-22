package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadHeading;

public class Heading extends Subsystem {

    private Gyro gyro;

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadHeading());
    }

    public Heading() {
        gyro = new Gyro(RobotMap.GYRO);
    }

    public void setZero() {
        gyro.reset();
    }

    public double readHeading() {
        double heading = gyro.getAngle();
        heading %= 360;
        heading = Math.abs(360);
        SmartDashboard.putDouble("Heading Reading", heading);
        return heading;
    }
}
