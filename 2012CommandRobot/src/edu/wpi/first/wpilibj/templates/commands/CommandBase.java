package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.GlobalState;
import edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    // Static operator interface handle
    public static OI oi;
    // Static instance of all subsystems
    public static HoodAngle hoodAngle = new HoodAngle();
    public static Drive drive = new Drive();
    public static Elevator elevator = new Elevator();
    public static Shooter shooter = new Shooter();
    public static ShooterEncoder shooterEncoder = new ShooterEncoder();
    public static Loader loader = new Loader();
    public static Switches switches = new Switches();
    public static Gravity gravity = new Gravity();
    public static Heading heading = new Heading();
    public static Arm arm = new Arm();
    public static Vin vin = new Vin();
    public static Distance distance = new Distance();
    public static GlobalState globalState = new GlobalState();
    public static Hood hood = new Hood();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(drive);
        SmartDashboard.putData(elevator);
        SmartDashboard.putData(shooter);
        SmartDashboard.putData(loader);
        SmartDashboard.putData(switches);
        SmartDashboard.putData(gravity);
        SmartDashboard.putData(heading);
        SmartDashboard.putData(arm);
        SmartDashboard.putData(vin);
        SmartDashboard.putData(distance);
        SmartDashboard.putData(shooterEncoder);
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
