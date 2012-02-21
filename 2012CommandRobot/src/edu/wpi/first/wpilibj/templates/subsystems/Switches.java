package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadSwitches;

public class Switches extends Subsystem {

    // The switch at the bottom of the elevator
    private DigitalInput elevatorBottomSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_SWITCH);
    // The switch at the top of the elevator
    private DigitalInput elevatorTopSwitch = new DigitalInput(RobotMap.ELEVATOR_TOP_SWITCH);
    // The switch at one ball length above the bottom of the elevator
    private DigitalInput elevatorMidSwitch = new DigitalInput(RobotMap.ELEVATOR_MID_SWITCH);

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadSwitches());
    }

    public Switches() {
    }

    public void readSwtiches() {
        boolean state;

        // Read the joystick-drive-enable toggle
        CommandBase.globalState.updateDriveEnabled(CommandBase.oi.isDriveEnablePressed());
        SmartDashboard.putBoolean("Drive Enabled", CommandBase.globalState.isDriveEnabled());

        // Read the drive-backwards toggle
        CommandBase.globalState.updateDriveBackwards(CommandBase.oi.isDriveBackwardsPressed());
        SmartDashboard.putBoolean("Drive Backwards", CommandBase.globalState.isDriveBackwards());

        // Read the balance-mode toggle
        // Reset to joystick control if driving is disabled
        if (CommandBase.globalState.isDriveEnabled()) {
            CommandBase.globalState.updateBalanceEnabled(CommandBase.oi.isBalanceEnablePressed());
        } else {
            CommandBase.globalState.setBalanceEnabled(false);
        }
        SmartDashboard.putBoolean("Balance Mode Enabled", CommandBase.globalState.isBalanceEnabled());

        // Read the ball-handing-enable toggle
        CommandBase.globalState.updateBallHandlingEnabled(CommandBase.oi.isBallHandlingEnablePressed());
        SmartDashboard.putBoolean("Ball Handling Enabled", CommandBase.globalState.isBallHandlingEnabled());

        // Read the shoot/load toggle
        // Reset to load if ball handling is disabled
        if (CommandBase.globalState.isBallHandlingEnabled()) {
            CommandBase.globalState.updateShootMode(CommandBase.oi.isShootModePressed());
        } else {
            CommandBase.globalState.setShootMode(false);
        }
        SmartDashboard.putBoolean("Shoot Mode Enabled", CommandBase.globalState.isShootMode());

        // Is a ball ready to be raised by the elevator?
        // On leading edge, count the ball as raised
        // On trailing edge, add to ballsQueued
        state = elevatorBottomSwitch.get();
        if (!(CommandBase.globalState.readyToQueue()) && state) {
            CommandBase.globalState.loadedBall();
            CommandBase.globalState.queuedBall();
        }
        CommandBase.globalState.setReadyToQueue(state);
        SmartDashboard.putBoolean("Elevator bottom switch", state);

        // On the trailing edge, remove the ball from the raise queue
        state = elevatorMidSwitch.get();
        if (CommandBase.globalState.readyToDequeue() && !state) {
            CommandBase.globalState.dequeuedBall();
        }
        CommandBase.globalState.setReadyToDequeue(state);
        SmartDashboard.putBoolean("Elevator mid switch", state);


        // Is a ball ready to shoot?
        // On trailing edge, count the ball as shot
        state = elevatorTopSwitch.get();
        if (CommandBase.globalState.readyToShoot() && (!state)) {
            CommandBase.globalState.unloadedBall();
        }
        CommandBase.globalState.setReadyToShoot(state);
        SmartDashboard.putBoolean("Elevator top switch", state);

        // A handy place for debug output
        SmartDashboard.putInt("Balls in queue", CommandBase.globalState.getBallsInQueue());
        SmartDashboard.putInt("Balls in control", CommandBase.globalState.ballsInControl());
    }
}