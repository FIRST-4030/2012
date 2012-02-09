package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
	public static final int JOYSTICK_DRIVE = 1;
	
	public static final int BUTTON_SHOOT = 1;
	public static final int BUTTON_DRIVE = 3;
	public static final int BUTTON_LOAD = 2;

	public static final int MOTOR_DRIVE_LEFT = 1;
	public static final int MOTOR_DRIVE_RIGHT = 2;
	public static final int MOTOR_SHOOTER = 3;
	public static final int MOTOR_ELEVATOR = 5;
	public static final int MOTOR_LOADER = 6;

	public static final double SHOOTER_SPEED = 1000;
	public static final double SHOOTER_P_GAIN = 0.1;
	public static final double SHOOTER_I_GAIN = 0.01;
	public static final double SHOOTER_D_GAIN = 0.0;

	public static final double ELEVATOR_SPEED_SHOOT = 1.0;
	public static final double ELEVATOR_SPEED_LOAD = 1.0;

	public static final double LOADER_SPEED = 1.0;
}
