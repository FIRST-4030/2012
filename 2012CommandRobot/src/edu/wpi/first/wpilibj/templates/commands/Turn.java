package edu.wpi.first.wpilibj.templates.commands;

public class Turn extends CommandBase {

    private boolean started = false;

    public Turn() {
        requires(drive);
    }

    public void turnTo(double angle) {
        double newHeading = globalState.getHeading() + angle;
        drive.turn(newHeading);
        started = true;
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        if (started) {
            return drive.pidComplete();
        }
        return false;
    }

    protected void end() {
        drive.stop();
        started = false;
    }

    protected void interrupted() {
        this.end();
    }
}
