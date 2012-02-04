package org.usfirst.frc0.subsystems;

import org.usfirst.frc0.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Shooter extends PIDSubsystem {

	private Jaguar shooter;

	public Shooter() {
		super("shooter", RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN,
				RobotMap.SHOOTER_D_GAIN);
		this.disable();
		this.setSetpoint(RobotMap.SHOOTER_SPEED);

		shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);
	}

	protected double returnPIDInput() {
		/* TODO: Read the shooter encoder */
		return 0;
	}

	protected void usePIDOutput(double output) {
		shooter.set(output);
	}

	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}
}
