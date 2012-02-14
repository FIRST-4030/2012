package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	// Input interfaces
	private Joystick stick;
	private JoystickButton load;
	private JoystickButton shoot;
	private JoystickButton joystickEnabled;
        private JoystickButton shootMode;
		
	public OI() {
		// Map the primary joystick
		stick = new Joystick(RobotMap.JOYSTICK_DRIVE);
		
		// Load when button 1 in depressed
		load = new JoystickButton(stick, RobotMap.BUTTON_LOAD);
		load.whileHeld(new Load());
		
		// Shoot when the trigger is pulled
		shoot = new JoystickButton(stick, RobotMap.BUTTON_SHOOT);
		shoot.whileHeld(new Shoot());
		
		// Toggle joystick driving mode
		joystickEnabled = new JoystickButton(stick, RobotMap.BUTTON_DRIVE);
                
                // Toggle the shoot/load mode
                shootMode = new JoystickButton(stick, RobotMap.BUTTON_SHOOT_MODE);
 	}
	
	public Joystick getDriveJoystick() {
		return stick;
	}
	
	public boolean isShootModePressed() {
		return shootMode.get();
	}
	
	public boolean isJoystickEnablePressed() {
		return joystickEnabled.get();
	}
}
