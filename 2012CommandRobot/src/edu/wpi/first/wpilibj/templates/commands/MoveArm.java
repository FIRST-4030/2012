package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveArm extends CommandBase {
    private boolean forceDown=false;
    public MoveArm() {
        requires(arm);
        requires(armlock);
    }
    public MoveArm(boolean forceDown){
        this();
        this.forceDown=forceDown;
        
    }

    protected void initialize() {
        double mod = RobotMap.ARMLOCK_MOD;
        if (arm.isArmUp()) {
            mod = 0;
        }
        setTimeout(RobotMap.ARM_TIMEOUT + mod);
    }

    protected void execute() {
        armlock.unlock();
        if (forceDown){
        arm.down();    
        }else if (!arm.isArmUp()) {
            if (timeSinceInitialized() > RobotMap.ARMLOCK_MOD) {
                arm.toggle();
            }
        } else {
            arm.toggle();
        }

    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        arm.stop();
        if (!arm.isArmUp()||forceDown) {
            armlock.lock();
            forceDown=false;
        }

    }

    protected void interrupted() {
        arm.stop();
    }
}
