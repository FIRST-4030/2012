package edu.wpi.first.wpilibj.templates.commands;

public class ReadVin extends CommandBase {

    public ReadVin() {
        requires(vin);
    }

    protected void initialize() {
    }

    protected void execute() {
        globalState.setVin(vin.readVoltage());
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
