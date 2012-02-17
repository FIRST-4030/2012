package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class Load extends CommandBase {

    public Load() {
        requires(elevator);
        requires(loader);
    }

    protected void initialize() {
    }

    protected void execute() {

        /*
         * Raise the elevator if any only if:
         *
         * We have a ball between the bottom and middle elevator switches We do
         * not have a ball at the top of the elevator
         */
        if (!globalState.readyToShoot() && globalState.getBallsInQueue() > 0) {
            elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        } else {
            elevator.stop();
        }
        loader.run(RobotMap.LOADER_SPEED);
    }

    public boolean isElevatorRunning() {
        if (elevator.isRunning()) {
            return true;
        }
        return false;
    }

    /*
     * Stop loading if we have a ball at the top of the elevator or we cannot
     * legally pick up more balls
     */
    protected boolean isFinished() {
        if (globalState.readyToShoot()) {
            return true;
        } else if (globalState.canLoadMoreBalls() && globalState.getBallsInQueue() < 1) {
            return true;
        }
        return false;
    }

    protected void end() {
        elevator.stop();
        loader.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
