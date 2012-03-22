package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;

public class Autoshoot extends CommandBase {

    private final static int STATE_CAMERA = 0;
    private final static int STATE_TURN = 1;
    private final static int STATE_SPIN = 2;
    private final static int STATE_SHOOT = 3;
    private int STATE = STATE_CAMERA;
    private boolean failed = false;
    private Turn turn;
    private Command shoot;
    private Command image;

    public Autoshoot() {
    }

    protected void initialize() {
        turn = new Turn();
        shoot = new Shoot();
        image = new RefreshCameraImage();
        this.end();
        globalState.setAutoshoot(true);
    }

    protected void execute() {
        switch (STATE) {
            // Request a new image analysis
            case STATE_CAMERA:
                image.start();
                STATE = STATE_TURN;

            // Wait for the camera to return target data, then turn
            case STATE_TURN:
                if (image.isRunning()) {
                    return;
                }
                if (!globalState.targetVisible()) {
                    failed = true;
                    return;
                }

                turn.start();
                turn.turnTo(globalState.getHeading() + globalState.angleToTarget());
                STATE = STATE_SPIN;

            // Wait for the robot to face the target, then get the shooter up-to-speed
            case STATE_SPIN:
                if (turn.isRunning()) {
                    return;
                }
                shooter.setDistance(globalState.distanceToTarget());
                STATE = STATE_SHOOT;

            // Wait for the shooter to get up-to-speed, then shoot as long as there are balls available
            case STATE_SHOOT:
                if (!shooter.atSpeed()) {
                    return;
                }
                if (globalState.readyToShoot()) {
                    shoot.start();
                }
        }
    }

    protected boolean isFinished() {
        if (!globalState.isDriveEnabled()) {
            return true;
        } else if (!globalState.isBallHandlingEnabled()) {
            return true;
        } else if (globalState.ballsInControl() < 1) {
            return true;
        } else if (failed) {
            return true;
        }
        return false;
    }

    protected void end() {
        turn.cancel();
        shoot.cancel();
        image.cancel();
        failed = false;
        globalState.setAutoshoot(false);
    }

    protected void interrupted() {
        this.end();
    }
}
