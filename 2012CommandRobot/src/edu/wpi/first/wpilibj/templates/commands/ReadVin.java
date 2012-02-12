package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class ReadVin extends Command {

    public ReadVin() {
        requires(CommandBase.vin);
    }

    protected void initialize() {
    }

    protected void execute() {
        CommandBase.globalState.setVin(CommandBase.vin.readVoltage());
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
