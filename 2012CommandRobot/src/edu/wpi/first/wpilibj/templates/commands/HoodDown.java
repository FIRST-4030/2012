package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class HoodDown extends CommandBase {

    public HoodDown() {
    }

    protected void initialize() {
        setTimeout(RobotMap.HOOD_TIMEOUT);
        hood.start();
    }

    protected void execute() {
        hood.adjustSetpoint(-1.0 * RobotMap.HOOD_ADJUST_RATE);
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
