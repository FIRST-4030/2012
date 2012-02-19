package edu.wpi.first.wpilibj.templates.commands;

public class ReadGravity extends CommandBase {

    public ReadGravity() {
        requires(gravity);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setGravity(gravity.readGravity());
        globalState.setAccelX(gravity.readAccelX());
        globalState.setAccelY(gravity.readAccelY());
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
