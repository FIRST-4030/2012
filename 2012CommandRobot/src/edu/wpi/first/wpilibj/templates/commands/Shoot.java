package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class Shoot extends CommandBase {

    public Shoot() {
        requires(elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
        if (globalState.readyToShoot()) {
            elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
        } else {
            elevator.stop();
        }
    }

    protected boolean isFinished() {
        return !globalState.readyToShoot();
    }

    protected void end() {
        elevator.stop();
    }

    protected void interrupted() {
        this.end();
    }
}


