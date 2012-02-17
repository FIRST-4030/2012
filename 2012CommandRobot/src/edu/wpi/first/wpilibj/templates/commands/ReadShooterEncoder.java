/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

/**
 *
 * @author prog
 */
public class ReadShooterEncoder extends CommandBase {

    public ReadShooterEncoder() {
        requires(shooterEncoder);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setShooterRate(shooterEncoder.getRate());
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
