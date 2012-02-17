package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadGravity;

public class Gravity extends Subsystem {

    private ADXL345_I2C accel;

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadGravity());
    }

    public Gravity() {
        accel = new ADXL345_I2C(RobotMap.ACCELEROMETER, ADXL345_I2C.DataFormat_Range.k2G);
    }

    public double readGravity() {
        double grav = accel.getAcceleration(ADXL345_I2C.Axes.kZ);
        SmartDashboard.putDouble("Gravity", grav);
        return grav;
    }
}
