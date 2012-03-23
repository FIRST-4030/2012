package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class SpinShooter extends CommandBase {

    public SpinShooter() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.start();
    }

    protected void execute() {
        if (globalState.ballsInControl() < 1) {
            setTimeout(RobotMap.SHOOTER_SPINDOWN_TIME);
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
        shooter.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
