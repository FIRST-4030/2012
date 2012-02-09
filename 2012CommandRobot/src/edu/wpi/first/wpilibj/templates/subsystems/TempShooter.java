package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TempShooter extends Subsystem {

	Jaguar shooter;

	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}

	public TempShooter() {
		shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);
	}

	public void run(double speed) {
		shooter.set(speed);
	}

	public void stop() {
		shooter.stopMotor();
	}
}
