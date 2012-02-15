package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Drive extends PIDSubsystem {

    RobotDrive drive;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Drive() {
        super("drive", RobotMap.BALANCE_P_GAIN, RobotMap.BALANCE_I_GAIN,
                RobotMap.BALANCE_D_GAIN);
        this.disable();

        drive = new RobotDrive(RobotMap.MOTOR_DRIVE_LEFT,
                RobotMap.MOTOR_DRIVE_RIGHT);
        drive.setSafetyEnabled(false);
    }

    public void driveWithJoystick(Joystick stick) {
        drive.arcadeDrive(stick, true);
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getGravity();
    }

    protected void usePIDOutput(double output) {
        drive.tankDrive(output * RobotMap.BALANCE_MAX_SPEED, output * RobotMap.BALANCE_MAX_SPEED);
    }

    public void balance() {
        this.setSetpoint(0);
        this.enable();
    }

    public void stop() {
        this.disable();
        drive.stopMotor();
    }
}
