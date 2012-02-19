package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class HoodDown extends CommandBase {

    public HoodDown() {
    }

    protected void initialize() {
    }

    protected void execute() {
        hood.adjustSetpoint(-1.0 * RobotMap.HOOD_ADJUST_RATE);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
        this.end();
    }
}
