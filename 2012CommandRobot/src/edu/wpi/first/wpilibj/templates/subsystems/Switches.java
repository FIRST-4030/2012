package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogTrigger;
import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadSwitches;

public class Switches extends Subsystem {

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadSwitches());
    }

    public Switches() {
        initSwitches();
    }
    
    
    private void initSwitches(){
        this.balanceArmSwitches.setLimitsVoltage(1, 1);
        this.loaderFrontSwitch.setLimitsVoltage(1, 1);
        this.elevatorBottomSwitch.setLimitsVoltage(1, 1);
        this.elevatorTopSwitch.setLimitsVoltage(1, 1);
    }
    
    private AnalogTrigger balanceArmSwitches = new AnalogTrigger(RobotMap.BALANCE_ARM_SWITCHES);
    
    // The switch at the front of the loader
    private AnalogTrigger loaderFrontSwitch = new AnalogTrigger(RobotMap.LOADER_FRONT_SWITCH);
    // The switch at the bottom of the elevator
    private AnalogTrigger elevatorBottomSwitch = new AnalogTrigger(RobotMap.ELEVATOR_BOTTOM_SWITCH);
    // The switch at the top of the elevator
    private AnalogTrigger elevatorTopSwitch = new AnalogTrigger(RobotMap.ELEVATOR_TOP_SWITCH);

    public void readSwtiches() {
        boolean state=false;

        // Read the joystick-drive-enable toggle
        CommandBase.globalState.updateJoystickDriveEnabled(CommandBase.oi.isJoystickEnablePressed());
        SmartDashboard.putBoolean("Joystick Drive Enabled", CommandBase.globalState.isJoystickDriveEnabled());

        // Read balanceArm limit switches
        CommandBase.globalState.setArmSwitch(balanceArmSwitches.getTriggerState());

        // Read elevator ball switches
        // Checks the current state compared to the last state for switches
        state=loaderFrontSwitch.getTriggerState();
        if(!(CommandBase.globalState.isLoaderFrontSwitch())&&state){
            CommandBase.globalState.ballsInLoaderPlus();
        }
        CommandBase.globalState.setLoaderFrontSwitch(state);
        
        state=elevatorBottomSwitch.getTriggerState();
        if(!(CommandBase.globalState.isElevatorBottomSwitch())&&state){
            CommandBase.globalState.ballsInLoaderMinus();
        }
        CommandBase.globalState.setElevatorBottomSwitch(state);
        
        state=elevatorTopSwitch.getTriggerState();
        if(CommandBase.globalState.isElevatorTopSwitch()&&(!state)){
            //this indicates that a ball has just been moved into the shooting wheel from the switch at the top of the elevator
            CommandBase.globalState.shotBall();
        }
        CommandBase.globalState.setElevatorTopSwitch(state);
    }
}
