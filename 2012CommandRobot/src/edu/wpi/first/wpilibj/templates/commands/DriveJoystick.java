package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DriveJoystick extends CommandBase {

    public DriveJoystick() {
        requires(drive);
    }

    protected void initialize() {
    }

    protected void execute() {
        drive.driveWithJoystick(oi.getDriveJoystick());
    }

    protected boolean isFinished() {
        return !globalState.isJoystickDriveEnabled();
    }

    protected void end() {
        drive.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
