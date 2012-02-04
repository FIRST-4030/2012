package org.usfirst.frc0.subsystems;

import org.usfirst.frc0.RobotMap;
import org.usfirst.frc0.commands.DriveJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveMotors extends Subsystem {

	RobotDrive drive;

	protected void initDefaultCommand() {
		setDefaultCommand(new DriveJoystick());
	}

	public DriveMotors() {
		drive = new RobotDrive(RobotMap.MOTOR_DRIVE_LEFT,
				RobotMap.MOTOR_DRIVE_RIGHT);
		drive.setSafetyEnabled(false);
	}

	public void DriveWithJoystick(Joystick stick) {
		drive.arcadeDrive(stick);
	}
}
