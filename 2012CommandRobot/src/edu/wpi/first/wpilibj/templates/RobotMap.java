package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    public static final int JOYSTICK_DRIVE = 1;
    public static final int BUTTON_BALANCE = 2;
    public static final int BUTTON_DRIVE = 3;
    public static final int BUTTON_BACKWARDS = 4;
    public static final int DRIVE_SENSITIVITY = 2;

    public static final int JOYSTICK_BALL = 2;
    public static final int BUTTON_BALL_HANDLING = 3;
    public static final int BUTTON_SHOOT = 1;
    public static final int BUTTON_SHOOT_MODE = 2;
    
    public static final int MOTOR_DRIVE_LEFT = 1;
    public static final int MOTOR_DRIVE_RIGHT = 2;
    public static final int MOTOR_SHOOTER = 3;
    public static final int MOTOR_ELEVATOR = 5;
    public static final int MOTOR_LOADER = 6;

    public static final int RELAY_ARM = 1;
    public static final double ARM_TIMEOUT = 10.0;

    public static final double SHOOTER_SPEED = 1000;
    public static final double SHOOTER_P_GAIN = 0.1;
    public static final double SHOOTER_I_GAIN = 0.01;
    public static final double SHOOTER_D_GAIN = 0.0;
    
    public static final double ELEVATOR_SPEED_SHOOT = 0.5;
    public static final double ELEVATOR_SPEED_LOAD = 0.5;
    
    public static final double LOADER_SPEED = -1.0;
    
    public static final int MAX_BALLS = 3;
    
    public static final int LOADER_FRONT_SWITCH = 1;
    public static final int ELEVATOR_BOTTOM_SWITCH = 2;
    public static final int ELEVATOR_TOP_SWITCH = 3;
    public static final int BALANCE_ARM_SWITCHES = 4;
    public static final int ELEVATOR_MID_SWITCH = 8;
    public static final int ENCODER_CHANNEL_A=5;
    public static final int ENCODER_CHANNEL_B=6;
    public static final int HOOD_POTENTIOMETER=7;
    
    public static final int VIN = 1;
    public static final int RANGEFINDER = 2;
    public static final int GYRO = 3;
    
    public static final int ACCELEROMETER = 1;

    public static final double BALANCE_P_GAIN = 0.01;
    public static final double BALANCE_I_GAIN = 0.01;
    public static final double BALANCE_D_GAIN = 0.0;
    public static final double BALANCE_MAX_SPEED = 0.1;
}
