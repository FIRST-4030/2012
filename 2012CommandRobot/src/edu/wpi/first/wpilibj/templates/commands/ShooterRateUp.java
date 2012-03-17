package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class ShooterRateUp extends CommandBase {

    public ShooterRateUp() {
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.adjustSetpoint(1.0 * RobotMap.SHOOTER_ADJUST_RATE);
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        this.end();
    }
}
