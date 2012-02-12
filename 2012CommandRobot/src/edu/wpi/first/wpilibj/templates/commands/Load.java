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

        // Raise the elevator if and only if we have balls in control and no ball ready to shoot
        if (!globalState.readyToShoot() && globalState.ballsInControl() > 0) {
            elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        } else {
            elevator.stop();
        }
    }

    // We are never done loading
    protected boolean isFinished() {
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
