package edu.wpi.first.wpilibj.templates.subsystems;

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
    }

    public void readSwtiches() {

        // Read the joystick-drive-enable toggle
        CommandBase.globalState.updateJoystickDriveEnabled(CommandBase.oi.isJoystickEnablePressed());
        SmartDashboard.putBoolean("Joystick Drive Enabled", CommandBase.globalState.isJoystickDriveEnabled());

        // Read balanceArm limit switches

        // Read elevator ball switches
    }
}
