package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class HoodUp extends CommandBase {

    public HoodUp() {
    }

    protected void initialize() {
        setTimeout(RobotMap.HOOD_TIMEOUT);
        hood.start();
    }

    protected void execute() {
        hood.adjustSetpoint(RobotMap.HOOD_ADJUST_RATE);
        setTimeout(RobotMap.HOOD_TIMEOUT);
    }

    protected boolean isFinished() {
        if (isTimedOut()) {
            return true;
        }
        return false;
    }

    protected void end() {
        hood.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
