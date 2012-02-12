package edu.wpi.first.wpilibj.templates.commands;

public class ReadSwitches extends CommandBase {

    public ReadSwitches() {
        requires(switches);
    }

    protected void initialize() {
    }

    protected void execute() {
        switches.readSwtiches();
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
