package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class FindTarget extends CommandBase {

    private final static int STATE_CAMERA = 0;
    private final static int STATE_TURN = 1;
    private final static int STATE_SPIN = 2;
    private int STATE = STATE_CAMERA;
    private int numTurns = 0;
    private Turn turn;
    private Command image;

    public FindTarget() {
    }

    protected void initialize() {
        turn = new Turn();
        image = new RefreshCameraImage();
        this.end();
    }

    protected void execute() {
        switch (STATE) {
            // Request a new image analysis
            case STATE_CAMERA:
                image.start();
                STATE = STATE_TURN;

            // Wait for a new image, then turn a fixed amount
            case STATE_TURN:
                if (image.isRunning()) {
                    return;
                }
                turn.start();
                turn.turnTo(globalState.getHeading() + RobotMap.FIND_TARGET_TURN);
                STATE = STATE_SPIN;

            // Wait for the robot to turn, then check again for the targets
            case STATE_SPIN:
                if (turn.isRunning()) {
                    return;
                }
                STATE = STATE_CAMERA;
        }
    }

    protected boolean isFinished() {
        if (globalState.targetVisible()) {
            return true;
        } else if (numTurns * RobotMap.FIND_TARGET_TURN > 360) {
            return true;
        }
        return false;
    }

    protected void end() {
        turn.cancel();
        image.cancel();
        STATE = STATE_CAMERA;
        numTurns = 0;
    }

    protected void interrupted() {
        this.end();
    }
}
