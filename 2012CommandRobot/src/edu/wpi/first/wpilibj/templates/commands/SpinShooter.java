/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author prog
 */
public class SpinShooter extends CommandBase {

    private double speed;

    public SpinShooter(double speed) {
        requires(shooter);
        this.speed = speed;
    }

    public SpinShooter() {
        this(RobotMap.SHOOTER_SPEED);
    }

    protected void initialize() {
    }

    protected void execute() {
        shooter.setSetpoint(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        shooter.setSetpoint(0);
    }

    protected void interrupted() {
        this.end();
    }
}
