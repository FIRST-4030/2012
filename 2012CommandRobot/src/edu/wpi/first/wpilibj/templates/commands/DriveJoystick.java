package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class DriveJoystick extends Command {

    public DriveJoystick() {
        requires(CommandBase.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
        CommandBase.drive.driveWithJoystick(CommandBase.oi.getDriveJoystick());
    }

    protected boolean isFinished() {
        return CommandBase.globalState.isJoystickDriveEnabled();
    }

    protected void end() {
        CommandBase.drive.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
