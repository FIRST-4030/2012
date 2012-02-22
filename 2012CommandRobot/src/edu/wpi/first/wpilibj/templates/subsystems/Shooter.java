package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Shooter extends PIDSubsystem {

    private Jaguar shooter;

    public Shooter() {
        super("shooter", RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN,
                RobotMap.SHOOTER_D_GAIN);
        this.setSetpoint(RobotMap.SHOOTER_SPEED);

        //this.setSetpointRange(-1.0 * RobotMap.BALANCE_MAX_SETPOINT, RobotMap.BALANCE_MAX_SETPOINT);
        this.getPIDController().setOutputRange(RobotMap.SHOOTER_CMD_SPEED_MIN, RobotMap.SHOOTER_CMD_SPEED_MAX);

        shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getShooterRate();

    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Shooter Command Speed", output);
        shooter.set(output);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public void start() {
        shooter.set(0.1);
        this.enable();
    }

    public void stop() {
        this.disable();
        shooter.stopMotor();
    }
}
