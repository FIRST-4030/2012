package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveArm extends CommandBase {

    private boolean armMoving = true;

    public MoveArm() {
        requires(arm);
        requires(armlock);
    }

    protected void initialize() {
        double mod=RobotMap.ARMLOCK_MOD;
        if(arm.isArmUp())mod=0;
        setTimeout(RobotMap.ARM_TIMEOUT+mod);
        this.armMoving = false;
    }

    protected void execute() {
        if(!arm.isArmUp()){
            armlock.unlock();
            if(timeSinceInitialized()>RobotMap.ARMLOCK_MOD){
                arm.toggle();
            }
        }else
        arm.toggle();
        
    }

    protected boolean isFinished() {
        boolean done = false;
        if (isTimedOut()) {
            done = true;
        } else if (!armMoving && !globalState.getArmSwitch()) {
            armMoving = true;
        } else if (armMoving && globalState.getArmSwitch()) {
            armMoving = false;
            //done = true;
        }
        
        return done;
    }

    protected void end() {
        
        arm.stop();
        if(!arm.isArmUp())armlock.lock();
        
    }

    protected void interrupted() {
        
        arm.stop();
    }
}
