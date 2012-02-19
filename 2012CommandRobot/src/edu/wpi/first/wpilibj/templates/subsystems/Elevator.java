package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

    Jaguar elevator;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Elevator() {
        elevator = new Jaguar(RobotMap.MOTOR_ELEVATOR);
    }

    public boolean isRunning() {
        if (elevator.get() != 0) {
            return true;
        }
        return false;
    }

    public void run(double speed) {
        SmartDashboard.putDouble("Elevator Command Speed", speed);
        elevator.set(speed);
    }

    public void stop() {
        elevator.stopMotor();
    }
}
