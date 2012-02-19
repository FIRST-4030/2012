package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class HoodUp extends CommandBase {

    public HoodUp() {
    }

    protected void initialize() {
    }

    protected void execute() {
        hood.adjustSetpoint(RobotMap.HOOD_ADJUST_RATE);
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
