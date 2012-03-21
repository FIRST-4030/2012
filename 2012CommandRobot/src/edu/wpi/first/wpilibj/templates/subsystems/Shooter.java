package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Shooter extends PIDSubsystem {

    private Jaguar shooter;
    private boolean adjusting;

    public Shooter() {
        super("shooter", RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN,
                RobotMap.SHOOTER_D_GAIN);
        this.setSetpoint(RobotMap.SHOOTER_SPEED_DEFAULT);
        this.getPIDController().setOutputRange(RobotMap.SHOOTER_CMD_SPEED_MIN, RobotMap.SHOOTER_CMD_SPEED_MAX);

        shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getShooterRate();

    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Shooter Command Rate", this.getSetpoint());
        SmartDashboard.putDouble("Shooter Command Speed", output);
        shooter.set(-1.0 * output);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public void adjustSetpoint(double delta) {
        double newSetpoint = this.getSetpoint() + delta;
        if (newSetpoint > RobotMap.SHOOTER_RATE_MAX) {
            newSetpoint = RobotMap.SHOOTER_RATE_MAX;
        } else if (newSetpoint < RobotMap.SHOOTER_RATE_MIN) {
            newSetpoint = RobotMap.SHOOTER_RATE_MIN;
        }
        this.setSpeed(newSetpoint);
    }

    public void setSpeed(double speed) {
        this.setSetpoint(speed);
        adjusting = true;
    }

    // Adjust the spinner rate to hit the top target at the specified distance
    public void setDistance(int distance) {
        this.setSetpoint(0);
    }

    // Return true once we've reached the desired speed
    public boolean atSpeed() {
        if (adjusting
                && Math.abs(this.getSetpoint() - this.getPosition())
                < RobotMap.SHOOTER_ZERO_THRESHOLD) {
            adjusting = false;
        }
        return adjusting;
    }

    public void start() {
        shooter.set(-0.1);
        this.enable();
    }

    public void stop() {
        this.disable();
        shooter.stopMotor();
    }
}
