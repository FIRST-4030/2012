package org.usfirst.frc0;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	private Joystick stick;
	
	public OI() {
		stick = new Joystick(RobotMap.JOYSTICK_DRIVE);
	}

	public Joystick getJoystick() {
		return stick;
	}
}
