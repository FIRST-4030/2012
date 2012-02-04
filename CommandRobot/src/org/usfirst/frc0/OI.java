package org.usfirst.frc0;

import org.usfirst.frc0.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	// Input interfaces
	private Joystick stick;
	private JoystickButton shoot;
	private JoystickButton joystickEnabled;
		
	public OI() {
		// Map the primary joystick
		stick = new Joystick(RobotMap.JOYSTICK_DRIVE_STICK);
		
		// Shoot when the trigger is pulled
		shoot = new JoystickButton(stick, RobotMap.JOYSTICK_SHOOT_BUTTON);
		shoot.whileHeld(new Shoot());
		
		// Toggle joystick driving with the top button
		joystickEnabled = new JoystickButton(stick, RobotMap.JOYSTICK_DRIVE_BUTTON);
 	}
	
	public Joystick getDriveJoystick() {
		return stick;
	}
	
	public boolean isShootPressed() {
		return shoot.get();
	}
	
	public boolean isJoystickEnablePressed() {
		return joystickEnabled.get();
	}
}
