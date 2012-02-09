package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;

public class TempShooter extends Subsystem {

    Jaguar shooter;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public TempShooter() {
        shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);

    }

    public void run(double speed) {
        shooter.set(speed);
    }

    public void stop() {
        final double STEP_INCREMENT = 0.1;
        final int SLEEP_LENGTH = 100;
        
        while (shooter.get() > 0) {
            if (shooter.get() <= STEP_INCREMENT) {
                shooter.set(0);
                break;
            } else {
                shooter.set(shooter.get() - STEP_INCREMENT);
                try {
                    Thread.sleep(SLEEP_LENGTH);
                } catch (InterruptedException e) {
                    // Oh noes! Sleep was interrupted!
                }
            }
        }
        shooter.stopMotor();
    }
}
