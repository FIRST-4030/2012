package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Arm extends Subsystem {

	Relay arm;

	protected void initDefaultCommand() {
		setDefaultCommand(null);
	}

	public Arm() {
		arm = new Relay(RobotMap.RELAY_ARM);
	}
        
        public void toggle() {
            if (CommandBase.globalState.isArmUp()) {
                this.down();
            } else {
                this.up();
            }
        }

	public void up() {
		arm.set(Value.kForward);
	}

	public void down() {
		arm.set(Value.kReverse);
	}
        
        public void stop() {
		arm.set(Value.kOff);
	}
}
