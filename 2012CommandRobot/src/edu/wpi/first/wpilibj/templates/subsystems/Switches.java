package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadSwitches;

public class Switches extends Subsystem {

    private DigitalInput balanceArmSwitches = new DigitalInput(RobotMap.BALANCE_ARM_SWITCHES);
    // The switch at the front of the loader
    private DigitalInput loaderFrontSwitch = new DigitalInput(RobotMap.LOADER_FRONT_SWITCH);
    // The switch at the bottom of the elevator
    private DigitalInput elevatorBottomSwitch = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_SWITCH);
    // The switch at the top of the elevator
    private DigitalInput elevatorTopSwitch = new DigitalInput(RobotMap.ELEVATOR_TOP_SWITCH);
    // The switch at one ball length above the bottom of the elevator
    //private DigitalInput elevatorMidSwitch = new DigitalInput(RobotMap.ELEVATOR_MID_SWITCH);

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadSwitches());
    }

    public Switches() {
    }

    public void readSwtiches() {
        boolean state;

        // Read the joystick-drive-enable toggle
        CommandBase.globalState.updateJoystickDriveEnabled(CommandBase.oi.isJoystickEnablePressed());
        SmartDashboard.putBoolean("Joystick Drive Enabled", CommandBase.globalState.isJoystickDriveEnabled());

        // Read balanceArm limit switches
        CommandBase.globalState.setArmSwitch(balanceArmSwitches.get());

        // Is a ball ready to load?
        // On leading edge, count the ball as loaded
        // TODO: This will need special handling if we have a ball-reject case
        state = loaderFrontSwitch.get();
        if (!(CommandBase.globalState.readyToLoad()) && state) {
            CommandBase.globalState.loadedBall();
        }
        CommandBase.globalState.setReadyToLoad(state);
        SmartDashboard.putBoolean("Ready to load", state);

        // Is a ball ready to be raised by the elevator?
        // On leading edge, count the ball as raised
        // On trailing edge, add to ballsAboveBottomOfElevator
        state = elevatorBottomSwitch.get();
        if (!(CommandBase.globalState.readyToRaise()) && state) {
            CommandBase.globalState.raisedBall();
        }else if((CommandBase.globalState.readyToRaise()) && !state){
            CommandBase.globalState.ballsAboveBottomOfElevatorPlus();
        }
        CommandBase.globalState.setReadyToRaise(state);
        SmartDashboard.putBoolean("Ready to raise", state);

        // Is a ball queued and ready to shoot?
        // On leading edge, count the ball as shot
        state = elevatorTopSwitch.get();
        if (CommandBase.globalState.readyToShoot() && (!state)) {
            CommandBase.globalState.unloadedBall();
        }
        CommandBase.globalState.setReadyToShoot(state);
        SmartDashboard.putBoolean("Ready to shoot", state);
        
        
//        state = elevatorMidSwitch.get();
        if (CommandBase.globalState.getBallsAboveBottomOfElevator()>0 && CommandBase.globalState.isDoneRaising() && !state){
            CommandBase.globalState.ballsAboveBottomOfElevatorMinus();
        }
    }
}