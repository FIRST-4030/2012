package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class SpinShooter extends CommandBase {
    private boolean waiting=false;
    public SpinShooter() {
        requires(shooter);
    }

    protected void initialize() {
        waiting=false;
        shooter.start();
    }

    protected void execute() {
        if (globalState.ballsInControl() < 1 && !waiting) {
            setTimeout(timeSinceInitialized()+RobotMap.SHOOTER_SPINDOWN_TIME);
            waiting=true;
        }
    }

    protected boolean isFinished() {
        if (!globalState.isBallHandlingEnabled()) {
            return true;
        } else if (!globalState.isShootMode() && !globalState.isAutoshootEnabled()) {
            return true;
        } else if (isTimedOut()) {            
            return true;
        }
        return false;
    }

    protected void end() {
        waiting=false;
        shooter.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
