package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
//import edu.wpi.first.wpilibj.templates.commands.MoveHood;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendablePIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hood extends PIDSubsystem {

    Victor hood;

    public Hood() {
        super("HoodAngle", RobotMap.HOOD_P_GAIN, RobotMap.HOOD_I_GAIN, RobotMap.HOOD_D_GAIN);
        this.setSetpointRange(RobotMap.HOOD_ANGLE_MIN, RobotMap.HOOD_ANGLE_MAX);
        this.setSetpoint((RobotMap.HOOD_ANGLE_MIN + RobotMap.HOOD_ANGLE_MAX) / 2);
        this.getPIDController().setTolerance(.01);
        hood = new Victor(RobotMap.MOTOR_HOOD);
    }

    public void initDefaultCommand() {
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getHoodAngle();
    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Hood Command Speed", output);
        SmartDashboard.putDouble("Hood Setpoint", this.getSetpoint());

        // Prevent hood overruns
        if (CommandBase.globalState.getHoodAngle() >= RobotMap.HOOD_ANGLE_MAX
                || RobotMap.HOOD_ANGLE_MAX <= RobotMap.HOOD_ANGLE_MIN) {
            output = 0;
        }
        hood.set(output);
    }

    public void adjustSetpoint(double delta) {
        this.setSetpoint(this.getSetpoint() + delta);
        if (this.getSetpoint() > RobotMap.HOOD_ANGLE_MAX) {
            this.setSetpoint(RobotMap.HOOD_ANGLE_MAX);
        } else if (this.getSetpoint() < RobotMap.HOOD_ANGLE_MIN) {
            this.setSetpoint(RobotMap.HOOD_ANGLE_MIN);
        }
        SmartDashboard.putDouble("newsetpoint", delta);
        start();
    }

    public void start() {
        this.enable();
    }

    public void stop() {
        this.disable();
        hood.stopMotor();
    }
}
