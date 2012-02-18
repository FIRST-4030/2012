package edu.wpi.first.wpilibj.templates.commands;

public class ReadDistance extends CommandBase {

    public ReadDistance() {
        requires(distance);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setDistance(distance.readDistance());
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
