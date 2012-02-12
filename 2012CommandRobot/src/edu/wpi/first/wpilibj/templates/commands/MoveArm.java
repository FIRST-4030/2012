package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveArm extends CommandBase {

    private boolean armMoving = false;

    public MoveArm() {
        requires(arm);
    }

    protected void initialize() {
        setTimeout(RobotMap.ARM_TIMEOUT);
    }

    protected void execute() {
        arm.toggle();
    }

    protected boolean isFinished() {
        boolean done = false;
        if (isTimedOut()) {
            done = true;
        } else if (!armMoving && !globalState.getArmSwtich()) {
            armMoving = true;
        } else if (armMoving && globalState.getArmSwtich()) {
            armMoving = false;
            done = true;
        }
        
        // Toggle our stored state after a timeout or transit
        if (done) {
            globalState.setArmSwitch(!globalState.getArmSwtich());
        }
        return done;
    }

    protected void end() {
        arm.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
