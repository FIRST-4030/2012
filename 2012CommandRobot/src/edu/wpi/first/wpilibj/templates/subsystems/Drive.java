package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.DriveJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

    RobotDrive drive;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Drive() {
        drive = new RobotDrive(RobotMap.MOTOR_DRIVE_LEFT,
                RobotMap.MOTOR_DRIVE_RIGHT);
        drive.setSafetyEnabled(false);
    }

    public void driveWithJoystick(Joystick stick) {
        drive.arcadeDrive(stick, true);
    }

    public void stop() {
        drive.stopMotor();
    }
}
