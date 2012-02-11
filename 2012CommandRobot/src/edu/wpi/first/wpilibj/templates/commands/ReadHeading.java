package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ReadHeading extends Command {

    public ReadHeading() {
        requires(CommandBase.heading);
    }

    protected void initialize() {
    }

    protected void execute() {
        CommandBase.globalState.setHeading(CommandBase.heading.readHeading());
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
