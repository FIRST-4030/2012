package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Drive extends PIDSubsystem {

    RobotDrive drive;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Drive() {
        super("drive", RobotMap.BALANCE_P_GAIN, RobotMap.BALANCE_I_GAIN,
                RobotMap.BALANCE_D_GAIN);
        this.disable();

        drive = new RobotDrive(RobotMap.MOTOR_DRIVE_LEFT,
                RobotMap.MOTOR_DRIVE_RIGHT);
        drive.setSafetyEnabled(false);
    }

    public void driveWithJoystick(Joystick stick) {
        this.drive(stick.getX(), stick.getY(), RobotMap.DRIVE_SENSITIVITY, CommandBase.globalState.isDriveBackwards());
    }

    protected double returnPIDInput() {
        return CommandBase.globalState.getGravity();
    }

    protected void usePIDOutput(double output) {
        drive.tankDrive(output * RobotMap.BALANCE_MAX_SPEED, output * RobotMap.BALANCE_MAX_SPEED);
    }

    public void balance() {
        this.setSetpoint(0);
        this.enable();
    }

    public void stop() {
        this.disable();
        drive.stopMotor();
    }

    private double limit(double num) {
        if (num > 1.0) {
            return 1.0;
        }
        if (num < -1.0) {
            return -1.0;
        }
        return num;
    }

    /*
     * Provide an approximation of java.lang.Math.pow(), but preserve the sign
     */
    private double pow(double base, int exponent) {
        int count = 0;
        double value = 1.0;
        for (count = 1; count <= exponent; count++) {
            value *= base;
        }
        if (base < 0.0) {
            return value * -1.0;
        }
        return value;
    }

    private void drive(double moveValue, double rotateValue, int sensitivity, boolean reverse) {
        // local variables to hold the computed PWM values for the motors
        double leftMotorSpeed;
        double rightMotorSpeed;

        // Ensure inputs are in the -1.0 to 1.0 range
        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        // Provide increased sensitivity in low-level inputes
        moveValue = pow(moveValue, sensitivity);
        rotateValue = pow(rotateValue, sensitivity);

        // Reverse inputs if requested (for driving backward)
        if (reverse) {
            moveValue *= -1.0;
            rotateValue *= -1.0;
        }

        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            } else {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }

        // Feed raw left/right motor speeds
        drive.tankDrive(leftMotorSpeed, rightMotorSpeed);
    }
}
