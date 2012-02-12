package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class Load extends CommandBase {

    public Load() {
        requires(elevator);
        requires(loader);
    }

    protected void initialize() {
        this.setTimeout(2);
    }

    protected void execute() {

        // Raise the elevator if and only if we have balls in control and no ball ready to shoot
        if (!globalState.readyToShoot() && globalState.ballsInControl() > 0 && globalState.getBallsAboveBottomOfElevator()>0) {
            elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        } else {
            elevator.stop();
        }
        loader.run(RobotMap.LOADER_SPEED);
    }

    // Done loading when there are no balls in the loader, no balls in at the bottom of the elevator
    // OR readyToShoot() returns true.
    protected boolean isFinished() {
        return (globalState.getBallsInLoader()==0)&&(globalState.getBallsAboveBottomOfElevator()==0)&&(this.isTimedOut())||globalState.readyToShoot();
    }

    protected void end() {
        elevator.stop();
        loader.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
