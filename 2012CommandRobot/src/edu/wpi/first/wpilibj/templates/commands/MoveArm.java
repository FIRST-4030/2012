package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {

    private boolean armMoving = false;

    public MoveArm() {
        requires(CommandBase.arm);
    }

    protected void initialize() {
        setTimeout(RobotMap.ARM_TIMEOUT);
    }

    protected void execute() {
        CommandBase.arm.toggle();
    }

    protected boolean isFinished() {
        boolean done = false;
        if (isTimedOut()) {
            done = true;
        } else if (!armMoving && !CommandBase.globalState.getArmSwtich()) {
            armMoving = true;
        } else if (armMoving && CommandBase.globalState.getArmSwtich()) {
            armMoving = false;
            done = true;
        }
        
        // Toggle our stored state after a timeout or transit
        if (done) {
            CommandBase.globalState.setArmSwitch(!CommandBase.globalState.getArmSwtich());
        }
        return done;
    }

    protected void end() {
        CommandBase.arm.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
