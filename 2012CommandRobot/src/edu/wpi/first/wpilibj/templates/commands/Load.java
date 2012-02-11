package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Load extends Command {

    public Load() {
        requires(CommandBase.elevator);
        requires(CommandBase.loader);
    }

    protected void initialize() {
    }

    protected void execute() {

        // Raise the elevator if and only if we have balls in control and no ball ready to shoot
        if (!CommandBase.globalState.readyToShoot() && CommandBase.globalState.ballsInControl() > 0) {
            CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        } else {
            CommandBase.elevator.stop();
        }
    }

    // We are never done loading
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        CommandBase.elevator.stop();
        CommandBase.loader.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
