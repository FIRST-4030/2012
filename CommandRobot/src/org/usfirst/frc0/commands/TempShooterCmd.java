package org.usfirst.frc0.commands;

import org.usfirst.frc0.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class TempShooterCmd extends Command {

	public TempShooterCmd() {
	}

	protected void initialize() {
		requires(CommandBase.elevator);
		requires(CommandBase.shooter);
	}

	protected void execute() {
		CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
		CommandBase.tempShooter.run(1.0);
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
		CommandBase.shooter.disable();
	}

	protected void interrupted() {
		this.end();
	}

}
