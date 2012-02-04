package org.usfirst.frc0;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {	
	public static final int JOYSTICK_DRIVE_STICK = 1;
	public static final int JOYSTICK_SHOOT_BUTTON = Joystick.ButtonType.kTrigger.value;
	public static final int JOYSTICK_DRIVE_BUTTON = Joystick.ButtonType.kTop.value;
	
	public static final int MOTOR_DRIVE_LEFT = 1;
	public static final int MOTOR_DRIVE_RIGHT = 2;
}
