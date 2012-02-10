package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ReadSwitches extends Command {

    public ReadSwitches() {
        requires(CommandBase.switches);
    }

    protected void initialize() {
    }

    protected void execute() {
        CommandBase.switches.readSwtiches();
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
