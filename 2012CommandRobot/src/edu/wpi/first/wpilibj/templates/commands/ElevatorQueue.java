/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author Ingyram
 */
public class ElevatorQueue extends CommandBase {
    
    public ElevatorQueue() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.elevator);
        requires(CommandBase.loader);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if(CommandBase.globalState.getBallsInLoader()>0){
            if(CommandBase.globalState.isElevatorBottomSwitch()){
                CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
            }else{
                CommandBase.elevator.stop();
                CommandBase.loader.run(RobotMap.LOADER_SPEED);
            }
        }else{
            CommandBase.loader.stop();
            CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        }
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
        return CommandBase.globalState.isElevatorTopSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
        CommandBase.elevator.stop();
        CommandBase.loader.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
