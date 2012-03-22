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
public class TimeDrive extends CommandBase
{
    double timeout;
    double speed;
    
    public TimeDrive(double speed,double timeout)
    {
        requires(drive);
        this.speed=speed;
        this.timeout=timeout;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize()
    {
        setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        drive.drive(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end()
    {
        drive.drive(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        end();
    }
}
