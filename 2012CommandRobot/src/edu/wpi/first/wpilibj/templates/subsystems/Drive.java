package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends PIDSubsystem {

    private Jaguar left;
    private Jaguar right;
    private double grav = 0.0;
    private boolean onRamp = false;
    private boolean nearLevel = false;
    private double nearLevelGrav = 0.0;
    private boolean pastLevel = false;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Drive() {
        super("drive", RobotMap.BALANCE_P_GAIN, RobotMap.BALANCE_I_GAIN,
                RobotMap.BALANCE_D_GAIN);

        this.setSetpointRange(-1.0 * RobotMap.BALANCE_MAX_SETPOINT, RobotMap.BALANCE_MAX_SETPOINT);

        left = new Jaguar(RobotMap.MOTOR_DRIVE_LEFT);
        right = new Jaguar(RobotMap.MOTOR_DRIVE_RIGHT);
    }

    public void driveWithJoystick(Joystick stick) {
        this.drive(stick.getX(), stick.getY(), RobotMap.DRIVE_SENSITIVITY, CommandBase.globalState.isDriveBackwards());
    }

    protected double returnPIDInput() {
        double tempGrav = CommandBase.globalState.getGravity();
        if (Math.abs(tempGrav) < RobotMap.BALANCE_ZERO_THRESHOLD) {
            tempGrav = 0;
        }
        grav = tempGrav;

        // Do not engage the near-level detection until we are fully on the ramp
        // Disengage the near-level behavior anytime the ramp is fully tilted
        // Start past-level behavior if we were near-level and the sign of the gravity reading changes
        // 
        if (Math.abs(grav) > RobotMap.BALANCE_FALL_STARTS) {
            onRamp = true;
            nearLevel = false;
            pastLevel = false;
        } else if (onRamp && !nearLevel && !pastLevel && Math.abs(grav) < RobotMap.BALANCE_NEAR_LEVEL) {
            nearLevel = true;
            pastLevel = false;
            nearLevelGrav = grav;
        } else if (onRamp && nearLevel && !pastLevel && (nearLevelGrav * grav < 0)) {
            pastLevel = true;
            nearLevel = false;
        }

        // Reduce our travel speed when we're near-level or past-level
        if (nearLevel || pastLevel) {
            this.getPIDController().setOutputRange(-1.0 * RobotMap.BALANCE_MAX_SPEED_LOW, RobotMap.BALANCE_MAX_SPEED_LOW);
        } else {
            this.getPIDController().setOutputRange(-1.0 * RobotMap.BALANCE_MAX_SPEED_HIGH, RobotMap.BALANCE_MAX_SPEED_HIGH);
        }

        // Reset the PID control as we pass through 0
        // This isn't stickly necessary for PID in general,
        // but given our particular system it's a good plan
        if (grav == 0) {
            this.getPIDController().reset();
        }

        return grav;
    }

    protected void usePIDOutput(double output) {
        // Drive uphill if we are not yet fully on the ramp
        // Also drive uphill if we are not near-level or are past-level
        // If we are near-level (i.e. starting to fall) drive downhill at a fixed rate
        // (PID drive speed is adjusted in the returnPIDInput() method)
        if (nearLevel) {
            if (grav < 0) {
                this.set(-1.0 * RobotMap.BALANCE_NEAR_LEVEL_SPEED, RobotMap.BALANCE_NEAR_LEVEL_SPEED);

            } else {
                this.set(RobotMap.BALANCE_NEAR_LEVEL_SPEED, -1.0 * RobotMap.BALANCE_NEAR_LEVEL_SPEED);
            }
        } else {
            this.set(-1.0 * output, output);
        }
    }

    public void balance() {
        // Stop to get everything reset
        this.stop();

        // Then enable the PID control
        this.setSetpoint(0);
        this.enable();
    }

    private void set(double leftSpeed, double rightSpeed) {
        left.set(leftSpeed);
        right.set(rightSpeed);
        SmartDashboard.putDouble("Left Drive Speed", leftSpeed);
        SmartDashboard.putDouble("Right Drive Speed", rightSpeed);
    }

    public void stop() {
        // Reset our balance indicators whenever we stop
        onRamp = false;
        nearLevel = false;
        pastLevel = false;
        nearLevelGrav = 0.0;

        // Stop the PID regulation and both motors
        this.disable();
        left.stopMotor();
        right.stopMotor();
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
        int count;
        double value = 1.0;
        for (count = 1; count <= exponent; count++) {
            value *= base;
        }
        value = java.lang.Math.abs(value);
        if (base < 0.0) {
            return value * -1.0;
        }
        return value;
    }

    private void drive(double moveValue, double rotateValue, int sensitivity, boolean reverse) {
        // local variables to hold the computed PWM values for the motors
        double leftMotorSpeed;
        double rightMotorSpeed;

        moveValue *= RobotMap.DRIVE_SPEED_SCALE;
        rotateValue *= RobotMap.DRIVE_SPEED_SCALE;
        // rotateValue*=1;
        // Ensure inputs are in the -1.0 to 1.0 range
        moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        // Provide increased sensitivity in low-level inputes
        moveValue = pow(moveValue, sensitivity);
        rotateValue = pow(rotateValue, sensitivity);

        // Reverse inputs if requested (for driving backward)
        moveValue *= -1.0;
        if (reverse) {

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
        this.set(leftMotorSpeed, rightMotorSpeed);
    }
}
