package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Elevator extends Subsystem {

	Jaguar elevator;

	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}

	public Elevator() {
		elevator = new Jaguar(RobotMap.MOTOR_ELEVATOR);
	}

	public void run(double speed) {
		elevator.set(speed);
	}

	public void stop() {
		elevator.stopMotor();
	}
}
