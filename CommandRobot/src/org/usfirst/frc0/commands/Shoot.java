package org.usfirst.frc0.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {

	public Shoot() {
	}

	protected void initialize() {
		requires(CommandBase.ballTransport);
	}

	protected void execute() {
		/* TODO: Spin up the elevator and firing wheel */
	}

	/*
	 * This command is called while the button is held, then canceled
	 * automatically, so we're never ready to stop. If there's a use case to
	 * detect the end of the shooting sequence this should be updated to trigger
	 * on that event
	 */
	protected boolean isFinished() {
		return false;
	}

	protected void end() {

	}

	protected void interrupted() {
	}

}
