package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    // Input interfaces

    private Joystick driveStick;
    private Joystick ballStick;
    private JoystickButton shoot;
    private JoystickButton driveEnabled;
    private JoystickButton driveBackwards ;
    private JoystickButton balanceEnabled;
    private JoystickButton ballHandlingEnabled;
    private JoystickButton shootMode;
    private JoystickButton arm;

    public OI() {
        // Map the joysticks
        driveStick = new Joystick(RobotMap.JOYSTICK_DRIVE);
        ballStick = new Joystick(RobotMap.JOYSTICK_BALL);

        // Shoot when the trigger is pulled
        shoot = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOT);
        shoot.whenPressed(new Shoot());

        // Toggle joystick driving mode
        driveEnabled = new JoystickButton(driveStick, RobotMap.BUTTON_DRIVE);
        driveBackwards = new JoystickButton(driveStick, RobotMap.BUTTON_BACKWARDS);
        balanceEnabled = new JoystickButton(driveStick, RobotMap.BUTTON_BALANCE);

        // Toggle the shoot/load mode
        ballHandlingEnabled = new JoystickButton(ballStick, RobotMap.BUTTON_BALL_HANDLING);
        shootMode = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOT_MODE);
        
        // Balance arm
        arm = new JoystickButton(ballStick, RobotMap.BUTTON_ARM);
        arm.whenPressed(new MoveArm());
    }

    public Joystick getDriveJoystick() {
        return driveStick;
    }

    public boolean isShootModePressed() {
        return shootMode.get();
    }

    public boolean isDriveEnablePressed() {
        return driveEnabled.get();
    }

    public boolean isDriveBackwardsPressed() {
        return driveBackwards.get();
    }

    public boolean isBalanceEnablePressed() {
        return balanceEnabled.get();
    }

    public boolean isBallHandlingEnablePressed() {
        return ballHandlingEnabled.get();
    }
}
