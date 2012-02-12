package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class Shoot extends Command {

    public Shoot() {
        requires(CommandBase.elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (CommandBase.globalState.readyToShoot()) {
            CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
        } else {
            CommandBase.elevator.stop();
        }
    }

    protected boolean isFinished() {
        return !CommandBase.globalState.readyToShoot();
    }

    protected void end() {
        CommandBase.elevator.stop();
    }

    protected void interrupted() {
        this.end();
    }
}


