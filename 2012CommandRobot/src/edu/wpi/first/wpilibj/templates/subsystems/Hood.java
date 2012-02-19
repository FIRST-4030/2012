package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.MoveHood;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Hood extends PIDSubsystem {

    Victor hood;
    
    public Hood() {
        super("HoodAngle", RobotMap.HOOD_P_GAIN, RobotMap.HOOD_I_GAIN, RobotMap.HOOD_D_GAIN);
        this.setSetpoint(RobotMap.HOOD_ANGLE_MIN);
        hood = new Victor(RobotMap.MOTOR_HOOD);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new MoveHood());
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getHoodAngle();
    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Hood Command Speed", output);
    }
    
    public void adjustSetpoint(double delta) {
        double setpoint = this.getSetpoint() + delta;
        setpoint = Math.min(setpoint, RobotMap.HOOD_ANGLE_MAX);
        setpoint = Math.max(setpoint, RobotMap.HOOD_ANGLE_MIN);
        this.setSetpoint(setpoint);
    }

    public void start() {
        this.enable();
    }

    public void stop() {
        this.disable();
        hood.stopMotor();
    }
}
