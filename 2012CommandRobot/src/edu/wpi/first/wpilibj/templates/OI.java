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
    private JoystickButton hoodUp;
    private JoystickButton hoodDown;
    private JoystickButton ballup;
    private JoystickButton balldown;
    private JoystickButton image;
    private JoystickButton shooterUp;
    private JoystickButton shooterDown;
    private JoystickButton autoshoot;

    public OI() {
        // Map the joysticks
        driveStick = new Joystick(RobotMap.JOYSTICK_DRIVE);
        ballStick = new Joystick(RobotMap.JOYSTICK_BALL);
        JoystickButton img=new JoystickButton(driveStick,1);
        img.whenPressed(new RefreshCameraImage());
        // Shoot when the trigger is pulled
        shoot = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOT);
        shoot.whenPressed(new Shoot());

        shoot = new JoystickButton(driveStick, 9);
        shoot.whenPressed(new TakePicture());
        // Toggle joystick driving mode
        driveEnabled = new JoystickButton(driveStick, RobotMap.BUTTON_DRIVE);
        driveBackwards = new JoystickButton(driveStick, RobotMap.BUTTON_BACKWARDS);
        balanceEnabled = new JoystickButton(driveStick, RobotMap.BUTTON_BALANCE);

        // Toggle the shoot/load/autoshoot mode
        ballHandlingEnabled = new JoystickButton(ballStick, RobotMap.BUTTON_BALL_HANDLING);
        shootMode = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOT_MODE);
        autoshoot = new JoystickButton(ballStick, RobotMap.BUTTON_AUTOSHOOT);
        
        // Balance arm
        arm = new JoystickButton(ballStick, RobotMap.BUTTON_ARM);
        arm.whenPressed(new MoveArm());
        // Hood adjust
        hoodUp = new JoystickButton(ballStick, RobotMap.BUTTON_HOOD_UP);
        hoodUp.whileHeld(new HoodUp());
        hoodDown = new JoystickButton(ballStick, RobotMap.BUTTON_HOOD_DOWN);
        hoodDown.whileHeld(new HoodDown());
        
        // Shooter adjust
        shooterUp = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOTER_UP);
        shooterUp.whenPressed(new ShooterRateUp());
        shooterDown = new JoystickButton(ballStick, RobotMap.BUTTON_SHOOTER_DOWN);
        shooterDown.whenPressed(new ShooterRateDown());
        
        ballup = new JoystickButton(ballStick, RobotMap.MANUAL_BALL_INC);
        ballup.whenPressed(new ManualBallCountControl(true));
        
        balldown = new JoystickButton(ballStick, RobotMap.MANUAL_BALL_DEC);
        balldown.whenPressed(new ManualBallCountControl(false));
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
    
    public boolean isAutoshootPressed() {
        return autoshoot.get();
    }
}
