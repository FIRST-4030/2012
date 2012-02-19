package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveHood extends CommandBase {

    public MoveHood() {
        requires(hood);
    }

    protected void initialize() {
        hood.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        hood.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
