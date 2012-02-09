package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Loader extends Subsystem {

	Jaguar loader;

	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}

	public Loader() {
		loader = new Jaguar(RobotMap.MOTOR_LOADER);
	}

	public void run(double speed) {
		loader.set(speed);
	}

	public void stop() {
		loader.stopMotor();
	}
}
