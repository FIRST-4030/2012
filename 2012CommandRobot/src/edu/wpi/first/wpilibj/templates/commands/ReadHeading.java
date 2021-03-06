package edu.wpi.first.wpilibj.templates.commands;

public class ReadHeading extends CommandBase {

    public ReadHeading() {
        requires(heading);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setHeading(heading.readHeading());
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
