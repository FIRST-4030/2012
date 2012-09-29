package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;

public class Autoshoot extends CommandBase {

    private final static int STATE_CAMERA = 0;
    private final static int STATE_TURN = 1;
    private final static int STATE_SPIN = 2;
    private final static int STATE_SHOOT = 3;
    private final static int STATE_WAIT=4;
    private int STATE = STATE_CAMERA;
    private boolean failed = false;
    //private Turn turn;
    //private Command shoot;
    private RefreshCameraImage image;
    //private boolean noShoot;

    public Autoshoot() {;
        
    //    this(false);
    }
    
    protected void initialize() {
        //turn = new Turn();
        //shoot = new Shoot();
        image = new RefreshCameraImage();
        this.cancelCommands();
        globalState.setAutoshoot(true);
        STATE=STATE_CAMERA;
    }

    protected void execute() {
        SmartDashboard.putInt("Autoshoot Mode", STATE);
        switch (STATE) {
            // Request a new image analysis
            case STATE_CAMERA:
                if(globalState.ballsInControl()<1){
                    return;
                }
                SmartDashboard.putString("state", "camera");
                image.start();
                STATE = STATE_TURN;
                
            case STATE_TURN:
                if(!image.isFinished())return;
                
               /* 
                if(!(Math.abs(globalState.getAzimuth())<RobotMap.AZIMUTH_THRESHOLD)){
                    //last taken image is not aimed
                    SmartDashboard.putString("state", "turn");

                    if(!drive.isActive()){
                        System.out.println("setting turnto to "+globalState.getAzimuth());
                        drive.turn(globalState.getAzimuth());
                    }
                    if(drive.pidComplete()){
                        drive.stop();
                        STATE=STATE_CAMERA;
                        return;
                    }
                    
                    return;
                }*/
                STATE = STATE_SPIN;
                
            case STATE_SPIN:
                //drive.stop();
                SmartDashboard.putString("state", "spin");
        /*        if(!globalState.targetVisible()){
                    failed=true;
                    return;
                }
*/
                shooter.start();

                STATE = STATE_SHOOT;
                
            case STATE_SHOOT:
                SmartDashboard.putString("state", "shoot");
                if (!shooter.atSpeed()) {
                    elevator.stop();
                }else{
                    elevator.run(RobotMap.ELEVATOR_SPEED_SHOOT);
                }
                if(globalState.ballsInControl() < 1){
                    setTimeout(timeSinceInitialized()+RobotMap.SHOOTER_SPINDOWN_TIME);
                    STATE=STATE_WAIT;
                }
                break;
            case STATE_WAIT:
                SmartDashboard.putString("state", "wait");
            
        }
    }

    protected boolean isFinished() {
        if (!globalState.isDriveEnabled()) {
            return true;
        } else if (!globalState.isBallHandlingEnabled()) {
            return true;
        } else if (globalState.ballsInControl()<1&&isTimedOut()) {
            return true;
        } else return failed;
    }

    
    protected void cancelCommands(){
        STATE=STATE_CAMERA;
        //turn.cancel();
        //shoot.cancel();
        shooter.stop();
        image.cancel();
    }
    protected void end() {
        SmartDashboard.putString("state", "finished");
        STATE=STATE_CAMERA;
        cancelCommands();
        failed = false;
        globalState.setAutoshoot(false);
    }

    protected void interrupted() {
        this.end();
    }
}
