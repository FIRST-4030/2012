package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class Load extends Command {

    public Load() {
        requires(CommandBase.elevator);
        requires(CommandBase.loader);
    }
    

    protected void initialize() {
    }

    protected void execute() {
        // If there is a ball in the loader and there is a ball at the bottom of the elevator,
        // this moves the elevator until the ball isn't touching the switch
        if(CommandBase.globalState.isElevatorBottomSwitch()&&(CommandBase.globalState.getBallsInLoader()>0)){
            CommandBase.elevator.run(RobotMap.ELEVATOR_SPEED_LOAD);
        }else{
            CommandBase.elevator.stop();
        }
        CommandBase.loader.run(RobotMap.LOADER_SPEED);
    }

    /*
     * This command is called while the button is held, then canceled
     * automatically, so we're never ready to stop. If there's a use case to
     * detect the end of the loading sequence this should be updated to trigger
     * on that event
     * 
     * Its function is to collect balls from the front of the loader, 
     * and make room for a new ball if there is a ball at the bottom of the elevator
     * by moving the balls in the elevator up just enough for a new ball to fit.
     * The end result of this should be after 3 balls are loaded, they should all be 
     * fairly close together near the bottom of the elevator, at which point ElevatorQueue
     * should be called.
     */
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        CommandBase.elevator.stop();
        CommandBase.loader.stop();
    }

    protected void interrupted() {
        this.end();
    }
}
