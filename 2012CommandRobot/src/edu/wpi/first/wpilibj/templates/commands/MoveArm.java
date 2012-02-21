package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

public class MoveArm extends CommandBase {

    public MoveArm() {
        requires(arm);
        requires(armlock);
    }

    protected void initialize() {
        double mod = RobotMap.ARMLOCK_MOD;
        if (arm.isArmUp()) {
            mod = 0;
        }
        setTimeout(RobotMap.ARM_TIMEOUT + mod);
    }

    protected void execute() {
        if (!arm.isArmUp()) {
            armlock.unlock();
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
        if (!arm.isArmUp()) {
            armlock.lock();
        }

    }

    protected void interrupted() {
        arm.stop();
    }
}
