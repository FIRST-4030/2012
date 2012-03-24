package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class Autoshoot extends CommandBase {

    private final static int STATE_CAMERA = 0;
    private final static int STATE_TURN = 1;
    private final static int STATE_SPIN = 2;
    private final static int STATE_SHOOT = 3;
    private int STATE = STATE_CAMERA;
    private boolean failed = false;
    private Turn turn;
    //private Command shoot;
    private Command image;
    private boolean noShoot;

    public Autoshoot() {
        this(false);
    }
    
    public Autoshoot(boolean noShoot) {
        this.noShoot = noShoot;
    }

    protected void initialize() {
        turn = new Turn();
        //shoot = new Shoot();
        image = new RefreshCameraImage();
        this.cancelCommands();
        globalState.setAutoshoot(true);
    }

    protected void execute() {
        SmartDashboard.putInt("Autoshoot Mode", STATE);
        
        switch (STATE) {
            // Request a new image analysis
            case STATE_CAMERA:
                image.start();
                STATE = STATE_TURN;

            // Wait for the camera to return target data, then turn
            case STATE_TURN:
               /* if (image.isRunning()) {
                    return;
                }
                if (!globalState.targetVisible()) {
                    failed = true;
                    return;
                }

                turn.start();
                *///turn.turnTo(globalState.getHeading() + globalState.getAzimuth());
                STATE = STATE_SPIN;

            // Wait for the robot to face the target, then get the shooter up-to-speed
            case STATE_SPIN:
                /*if (turn.isRunning()) {
                    return;
                }*/
                
                shooter.setDistance((int)globalState.getCameraDistance());
                shooter.start();
                
                STATE = STATE_SHOOT;

            // Wait for the shooter to get up-to-speed, then shoot as long as there are balls available
            case STATE_SHOOT:
                /*if (noShoot) {
                    return;
                }*/
                
                if (!shooter.atSpeed()) {
                    elevator.stop();
                    return;
                }else{
                    elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
                    return;
                }
                /*
                if (globalState.readyToShoot()) {
                    shoot.start();
                }*/
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
        } else if (STATE == STATE_SHOOT) {
            return noShoot;
        }
        return false;
    }

    
    protected void cancelCommands(){
        turn.cancel();
        //shoot.cancel();
        image.cancel();
    }
    protected void end() {
        cancelCommands();
        failed = false;
        shooter.stop();
        globalState.setAutoshoot(false);
    }

    protected void interrupted() {
        this.end();
    }
}
