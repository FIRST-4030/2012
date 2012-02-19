package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveHood extends CommandBase {

    public MoveHood() {
        requires(hood);
    }

    protected void initialize() {
        setTimeout(RobotMap.HOOD_TIMEOUT);
    }

    protected void execute() {
        hood.start();
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
