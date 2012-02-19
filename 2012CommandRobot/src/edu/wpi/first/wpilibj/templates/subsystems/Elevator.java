package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator extends Subsystem {

    Jaguar elevator;
    private boolean running = false;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Elevator() {
        elevator = new Jaguar(RobotMap.MOTOR_ELEVATOR);
    }

    public boolean isRunning() {
        return running;
    }

    public void run(double speed) {
        SmartDashboard.putDouble("Elevator Command Speed", speed);
        running = true;
        elevator.set(speed);
    }

    public void stop() {
        running = false;
        elevator.stopMotor();
    }
}
