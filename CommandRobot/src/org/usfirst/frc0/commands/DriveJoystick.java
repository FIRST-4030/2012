package org.usfirst.frc0.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DriveJoystick extends Command {

	public DriveJoystick() {
		requires(CommandBase.driveMotors);
	}

	protected void initialize() {
	}

	protected void execute() {
		CommandBase.driveMotors.DriveWithJoystick(CommandBase.oi.getJoystick());
	}

	protected boolean isFinished() {
		/*
		 * TODO: Under some conditions we probably want to disengage
		 * DriveJoystick, but now we just drive forever
		 */
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
