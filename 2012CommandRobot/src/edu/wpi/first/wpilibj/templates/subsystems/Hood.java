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
        this.setSetpoint((RobotMap.HOOD_ANGLE_MIN+RobotMap.HOOD_ANGLE_MAX)/2);
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
        double tempAngle=CommandBase.globalState.getHoodAngle();
        hood.set(output);
        if(tempAngle>=RobotMap.HOOD_ANGLE_MAX){
            if(output<0){
                SmartDashboard.putBoolean("maxtripped", true);
                hood.set(0);
            }
        }else if(tempAngle<=RobotMap.HOOD_ANGLE_MIN){
            if(output>0){
                SmartDashboard.putBoolean("mintripped", true);
                hood.set(0);
            }
        }else{
            SmartDashboard.putBoolean("maxtripped", false);
            SmartDashboard.putBoolean("mintripped", false);
        }
    }

    public void adjustSetpoint(double delta) {
        //this.setSetpointRelative(delta);
        this.setSetpoint(this.getSetpoint()+delta);
        if(this.getSetpoint()>RobotMap.HOOD_ANGLE_MAX){
            this.setSetpoint(RobotMap.HOOD_ANGLE_MAX);
        }else if(this.getSetpoint()<RobotMap.HOOD_ANGLE_MIN){
            this.setSetpoint(RobotMap.HOOD_ANGLE_MIN);
        }
        SmartDashboard.putDouble("newsetpoint", delta);
        start();
    }

    public void start() {
        SmartDashboard.putString("hood Ajusting?","started" );
        this.enable();
    }

    public void stop() {
        SmartDashboard.putString("hood Ajusting?","stopped" );
        this.disable();
        hood.stopMotor();
    }
}
