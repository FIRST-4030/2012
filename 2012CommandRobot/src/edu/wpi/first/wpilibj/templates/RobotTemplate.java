/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    //Command autonomousCommand;
    private Command joystick;
    private Command balance;
    private Command load;
    private Command shoot;
    private Command elevator;
    private Command shooter;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        //autonomousCommand = new ExampleCommand();

        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        //autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        //autonomousCommand.cancel();

        joystick = new DriveJoystick();
        balance = new Balance();
        load = new Load();
        shooter = new SpinShooter();
        shoot = new Shoot();
        elevator = new ElevatorCmd();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        // Enable/disable drive
        if (CommandBase.globalState.isDriveEnabled()) {
            // Mode switch between joystick and balance
            if (CommandBase.globalState.isBalanceEnabled()) {
                if (!balance.isRunning()) {
                    joystick.cancel();
                    balance.start();
                }
            } else {
                if (!joystick.isRunning()) {
                    balance.cancel();
                    joystick.start();
                }
            }
        } else {
            balance.cancel();
            joystick.cancel();
        }

        // Enable/disable ball handling
        if (CommandBase.globalState.isBallHandlingEnabled()) {
            // Mode swtich between shooting and loading
            if (CommandBase.globalState.isShootMode()) {
                // Shoot (but not until the load elevator is done)
                if (!shooter.isRunning() && !((Load) load).isElevatorRunning()) {
                    load.cancel();
                    shooter.start();
                }
                // Restart the elevator anytime we have balls and no shoot-mode balls handler is running
                if (!shoot.isRunning() && !elevator.isRunning() && !CommandBase.globalState.readyToShoot() && CommandBase.globalState.ballsInControl() > 0) {
                    elevator.start();
                }
            } else {
                // Load
                if (!load.isRunning() && !CommandBase.globalState.readyToShoot() && CommandBase.globalState.canLoadMoreBalls()) {
                    shooter.cancel();
                    elevator.cancel();
                    load.start();
                }
            }
        } else {
            shooter.cancel();
            elevator.cancel();
            load.cancel();
        }
    }
}
