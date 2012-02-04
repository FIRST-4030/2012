package org.usfirst.frc0.subsystems;

import org.usfirst.frc0.RobotMap;

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
