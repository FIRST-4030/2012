/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author prog
 */
public class AutoRampKnockdown extends CommandBase
{
    private MoveArm moveArm;
    private TimeDrive timeDrive;
    private Autoshoot autoshoot;
    private boolean isfinished = false;
    
    public AutoRampKnockdown()
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drive);
        requires(arm);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        moveArm = new MoveArm(true);
        //setTimeout(RobotMap.AUTO_RAMP_TIMEOUT);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        if(!globalState.isAutoknockdown()){
            globalState.setAutoknockdown(true);
            moveArm.start();
            timeDrive = new TimeDrive(RobotMap.AUTO_RAMP_KNOCKDOWN_SPEED,RobotMap.AUTO_RAMP_TIMEOUT);
            timeDrive.start();
        }else if(drive.isOnRamp()){
            timeDrive = new TimeDrive(-RobotMap.AUTO_RAMP_KNOCKDOWN_SPEED/2,RobotMap.AUTO_RAMP_TIMEOUT/4);
        }else if(timeDrive.isFinished()){
            drive.drive(0);
            autoshoot = new Autoshoot();
            autoshoot.start();
            isfinished = true;
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return isfinished;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        globalState.setAutoknockdown(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        this.end();
    }
}
