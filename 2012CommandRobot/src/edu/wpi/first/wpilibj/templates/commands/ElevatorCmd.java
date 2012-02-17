package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

/**
*
* @author Ingyram
*/
public class ElevatorCmd extends CommandBase {
    
    public ElevatorCmd() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
    }
    
    /*
* This command should be called when the balls in the elevator are almost ready to be fired.
* It will move the balls, which should be near each other at the bottom, all the way to
* the top of the elevator, keeping them close together. It is inadvisable to call this command
* unless the balls will all be shot very soon, because after this command is called, loading
* more balls would cause a large undesirable space in the elevator shaft.
* Also, if this command is called while there is still a ball in the loader area, it will
* finish moving that ball into the elevator before it moves them all to the top.
*/

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return CommandBase.globalState.readyToShoot();
    }

    // Called once after isFinished returns true
    protected void end() {
        CommandBase.elevator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}