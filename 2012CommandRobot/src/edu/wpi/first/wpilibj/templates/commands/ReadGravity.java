package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ReadGravity extends Command {

    public ReadGravity() {
        requires(CommandBase.gravity);
    }

    protected void initialize() {
    }

    protected void execute() {
        CommandBase.globalState.setGravity(CommandBase.gravity.readGravity());
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
