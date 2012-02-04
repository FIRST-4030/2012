package org.usfirst.frc0.subsystems;

import org.usfirst.frc0.RobotMap;
import org.usfirst.frc0.commands.DriveJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {

	RobotDrive drive;

	protected void initDefaultCommand() {
		setDefaultCommand(new DriveJoystick());
	}

	public Drive() {
		drive = new RobotDrive(RobotMap.MOTOR_DRIVE_LEFT,
				RobotMap.MOTOR_DRIVE_RIGHT);
		drive.setSafetyEnabled(false);
	}

	public void driveWithJoystick(Joystick stick) {
		drive.arcadeDrive(stick);
	}
	
	public void stop() {
		drive.stopMotor();
	}
}
