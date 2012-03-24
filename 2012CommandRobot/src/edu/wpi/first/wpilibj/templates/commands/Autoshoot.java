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
    private RefreshCameraImage image;
    //private boolean noShoot;

    public Autoshoot() {
    //    this(false);
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
                STATE = STATE_SPIN;
                break;
            case STATE_SPIN:
                if(!image.isFinished())return;
                if(!globalState.targetVisible()){
                    failed=true;
                    return;
                }

                shooter.setDistance((int)globalState.getCameraDistance());
                shooter.start();
                
                STATE = STATE_SHOOT;
            case STATE_SHOOT:
                if (!shooter.atSpeed()) {
                    elevator.stop();
                }else{
                    elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
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
        } else return failed;
    }

    
    protected void cancelCommands(){
        turn.cancel();
        //shoot.cancel();
        image.cancel();
    }
    protected void end() {
        cancelCommands();
        failed = false;
        if(isFinished()){
            shooter.stop();
        }
        globalState.setAutoshoot(false);
    }

    protected void interrupted() {
        this.end();
    }
}
