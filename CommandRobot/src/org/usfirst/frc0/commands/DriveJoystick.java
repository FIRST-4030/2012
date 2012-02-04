package org.usfirst.frc0.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DriveJoystick extends Command {

	public DriveJoystick() {
	}

	protected void initialize() {
		requires(CommandBase.driveMotors);
	}

	protected void execute() {
		CommandBase.driveMotors.DriveWithJoystick(CommandBase.oi.getDriveJoystick());
	}

	protected boolean isFinished() {
		return CommandBase.globalState.isJoystickDriveEnabled();
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
