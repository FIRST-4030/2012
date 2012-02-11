package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Accelerometer;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadGravity;

public class Gravity extends Subsystem {

    private Accelerometer accel;

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadGravity());
    }

    public Gravity() {
        accel = new Accelerometer(RobotMap.ACCELEROMETER);

        // Assume that we start fairly level
        this.setLevel();
    }

    // This should be called before we start any balance activity
    // There is not method provided in the FRC framework to continously re-zero the reading
    public void setLevel() {
        AnalogChannel vin = new AnalogChannel(RobotMap.VIN);
        accel.setZero(vin.getVoltage());
    }

    public double readGravity() {
        double grav = accel.getAcceleration();
        SmartDashboard.putDouble("Gravity", grav);
        return grav;
    }
}
