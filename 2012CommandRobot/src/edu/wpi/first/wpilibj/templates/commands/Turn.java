package edu.wpi.first.wpilibj.templates.commands;

public class Turn extends CommandBase {

    private boolean started = false;

    public Turn() {
        setInterruptible(true);
        requires(drive);
    }

    public void turnTo(double angle) {
        //double newHeading = globalState.getHeading() + angle;
        drive.turn(angle);
        started = true;
    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        // Until we test this, we're always finished
        // It makes the command useless, but it lets autoshoot fire off balls in autonomous mode
        // And the if statement is just to make java happy about the early return
  
        if(true)return true;
        
        if (!CommandBase.globalState.isDriveEnabled()) {
            return true;
        } else if (started) {
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
