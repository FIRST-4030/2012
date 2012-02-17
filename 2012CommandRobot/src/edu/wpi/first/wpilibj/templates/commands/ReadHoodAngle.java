package edu.wpi.first.wpilibj.templates.commands;


public class ReadHoodAngle extends CommandBase {

    public ReadHoodAngle() {
        requires(hoodAngle);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setHoodAngle(hoodAngle.getPosition());
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
