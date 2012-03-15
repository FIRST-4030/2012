package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class SpinShooter extends CommandBase {

    private double speed;

    public SpinShooter() {
        requires(shooter);
    }

    protected void initialize() {
        shooter.start();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        if (!globalState.isShootMode()) {
            return true;
        }
        if (globalState.ballsInControl() < 1) {
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
