package org.usfirst.frc0.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc0.GlobalState;
import org.usfirst.frc0.OI;
import org.usfirst.frc0.subsystems.*;

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
	public static Drive drive = new Drive();
	public static Elevator elevator = new Elevator();
	public static Shooter shooter = new Shooter();
	public static Loader loader = new Loader();
        public static TempShooter tempShooter = new TempShooter();
	public static GlobalState globalState = new GlobalState();

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
	}

	public CommandBase(String name) {
		super(name);
	}

	public CommandBase() {
		super();
	}
}
