package edu.wpi.first.wpilibj.templates.commands;

public class Balance extends CommandBase {

    public Balance() {
        requires(drive);
    }

    protected void initialize() {
    }

    protected void execute() {
        drive.balance();
    }

    protected boolean isFinished() {
        if (!globalState.isDriveEnabled()) {
            return true;
        }
        if (!globalState.isBalanceEnabled()) {
            return true;
        }
        return false;
    }

    protected void end() {
        drive.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
