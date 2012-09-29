package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends PIDSubsystem {

    final private static int PID_MODE_BALANCE = 0;
    final private static int PID_MODE_TURN = 1;
    final private static double HEADING_MIN = 0;
    final private static double HEADING_MAX = 360;
    final private static double GRAV_MIN = -1.0;
    final private static double GRAV_MAX = 1.0;
    private Jaguar left;
    private Jaguar right;
    private int PID_MODE = PID_MODE_TURN;
    private double heading = 0.0;
    private double grav = 0.0;
    private boolean onRamp = false;
    private boolean nearLevel = false;
    private double nearLevelGrav = 0.0;
    private boolean pastLevel = false;
    private boolean lastDirection=true;

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public Drive() {
        super("drive", RobotMap.BALANCE_P_GAIN, RobotMap.BALANCE_I_GAIN,
                RobotMap.BALANCE_D_GAIN);

        left = new Jaguar(RobotMap.MOTOR_DRIVE_LEFT);
        right = new Jaguar(RobotMap.MOTOR_DRIVE_RIGHT);
    }

    public void driveWithJoystick(Joystick stick) {
        this.drive(stick.getX(), stick.getY(), RobotMap.DRIVE_SENSITIVITY, CommandBase.globalState.isDriveBackwards());
    }

    
    protected double returnPIDInput() {
        switch (PID_MODE) {
            case PID_MODE_TURN:
                double tempHeading = CommandBase.heading.readHeading();
                double tempSetpoint=this.getSetpoint();
                
                SmartDashboard.putDouble("needed heading", tempSetpoint);
                SmartDashboard.putDouble("current heading", tempHeading);
                
                //heading = tempHeading;
                return tempHeading;

            default:
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
    }
    /*- counterclockwise lower number
     *+ clockwise higher number
     * 
     */
    protected void usePIDOutput(double output) {
        
        //ignore direction and use pid only for magnitude
        output=Math.abs(output);
        double current = CommandBase.heading.readHeading();
        double needed=this.getSetpoint();
        SmartDashboard.putDouble("turn diff", Math.abs(current-needed));
        
        if(current>needed){
            
            SmartDashboard.putString("turnsec", "down");
            /*
            if((Math.abs(current-needed)>180)){
                output=+output;
            SmartDashboard.putInt("turnsec", 1);
            }else{
                output=-output;
                SmartDashboard.putInt("turnsec", 2);
            }
            */
            
        }else{
            output=-output;
            SmartDashboard.putString("turnsec", "up");
            
            /*
            if(!(Math.abs(current-needed)>180)){
                output=-output;
            SmartDashboard.putInt("turnsec", 3);
            }else{
f                output=+output;
                SmartDashboard.putInt("turnsec", 4);
            }
            */
        }
        if(output>=0!=lastDirection&&Math.abs(output)>.2){
            /*System.out.println("first"+(output>0)+","+lastDirection+" output is"+output);
            lastDirection=output>=0;
            System.out.println("second"+(output>0)+","+lastDirection+" output is"+output);
            double setPoint=this.getSetpoint();
            this.getPIDController().reset();
            this.getPIDController().setSetpoint(setPoint);
            this.getPIDController().enable();
            output=0;*/
        }
            //output=0;
          this.set(-output, -output);
    }

    
    public boolean isActive(){
        return this.getPIDController().isEnable();
    }
    // Return true if the current PID action is complete (i.e. within the zero threshold)
    public boolean pidComplete() {
        if (!this.getPIDController().isEnable()) {
            return true;
        }
        SmartDashboard.putString("is turning", ""+this.isActive());
        
        switch (PID_MODE) {
            case PID_MODE_TURN:
                
                
                if (Math.abs(Math.abs(this.getSetpoint())-Math.abs(CommandBase.globalState.getHeading())) < RobotMap.AZIMUTH_THRESHOLD) {
                    return true;
                }
            default:
                if (this.getSetpoint() == grav) {
                    return true;
                }
        }
        return false;
    }
    private int turncount=0;
    public void turn(double angle) {
        SmartDashboard.putInt("turncount", turncount++);
        // Stop to get everything reset
        this.stop();
        this.getPIDController().reset();
        // Then enable the PID control
        PID_MODE = PID_MODE_TURN;
        this.getPIDController().setPID(RobotMap.TURN_P_GAIN, RobotMap.TURN_I_GAIN,
                RobotMap.TURN_D_GAIN);
        
        this.setSetpointRange(HEADING_MIN, HEADING_MAX);
        
        this.getPIDController().setOutputRange(-1.0 * RobotMap.TURN_SPEED_MAX,  1.0 *RobotMap.TURN_SPEED_MAX);
        this.getPIDController().setInputRange(HEADING_MIN, HEADING_MAX);
        
        this.getPIDController().setContinuous(true);
        this.setSetpoint( (HEADING_MAX+CommandBase.globalState.getHeading()-angle)%HEADING_MAX);
        System.out.println("Setpoint is"+(HEADING_MAX+CommandBase.globalState.getHeading()-angle)%HEADING_MAX);
        System.out.println("Angle is"+angle);
        
        
        this.enable();
        
        /**/
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
    public void drive(double moveValue){
        drive(moveValue,0,1,false);
    }

    private void drive(double moveValue, double rotateValue, int sensitivity, boolean reverse) {
        double deadspot=.3;
        if(this.isActive()){
            return;
        }
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
    //System.out.println("driving");
        // Feed raw left/right motor speeds
        this.set(leftMotorSpeed, rightMotorSpeed);
    }
    
    public boolean isOnRamp(){
        return onRamp;
    }
}
