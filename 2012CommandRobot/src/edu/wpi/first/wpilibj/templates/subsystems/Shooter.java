package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.templates.RobotMap;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

public class Shooter extends PIDSubsystem {

    private Jaguar shooter;
    private boolean adjusting;

    public Shooter() {
        super("shooter", RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN,
                RobotMap.SHOOTER_D_GAIN);
        this.setSetpoint(RobotMap.SHOOTER_SPEED_DEFAULT);
        this.getPIDController().setOutputRange(RobotMap.SHOOTER_CMD_SPEED_MIN, RobotMap.SHOOTER_CMD_SPEED_MAX);

        shooter = new Jaguar(RobotMap.MOTOR_SHOOTER);
    }

    protected double returnPIDInput() {
        double shooterRate=CommandBase.globalState.getShooterRate();
        SmartDashboard.putDouble("shooter encoder", shooterRate);
        return shooterRate;

    }

    protected void usePIDOutput(double output) {
        SmartDashboard.putDouble("Shooter Command Rate", this.getSetpoint());
        SmartDashboard.putDouble("Shooter Command Speed", output);
        
        shooter.set(1.0 * output);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(null);
    }

    public void adjustSetpoint(double delta) {
        double newSetpoint = this.getSetpoint() + delta;
        if (newSetpoint > RobotMap.SHOOTER_RATE_MAX) {
            newSetpoint = RobotMap.SHOOTER_RATE_MAX;
        } else if (newSetpoint < RobotMap.SHOOTER_RATE_MIN) {
            newSetpoint = RobotMap.SHOOTER_RATE_MIN;
        }
        this.setSpeed(newSetpoint);
    }

    public void setSpeed(double speed) {
        System.out.println(speed);
        this.setSetpoint(speed);
        adjusting = true;
    }

    // Return true once we've reached the desired speed
    public boolean atSpeed() {
        if (Math.abs(this.getSetpoint() - this.getPosition())
                < RobotMap.SHOOTER_ZERO_THRESHOLD) {
            adjusting = false;
        }
        return !adjusting;
    }

    // Adjust the spinner rate to hit the top target at the specified distance
    public void setDistance(int distance) {
        // These constants should be someplace else, and in a real data structure,
        // but for ease-of-setup let's not. These lists must be the same length
        // and must be strictly ordered from shortest distance to longest
        //int distances[]=new int[10];
        //int speeds[]=new int[10];
        //for(int i=0;i<240;i+=4)
        
        //OLD DATA from competition
        //double distances[] = {110,122,142,154,197,205};
        //double speeds[] =    { 28, 30, 34, 34, 44, 52};
        //NEW DATA updated Sept 012
        System.out.println(distance);
        double distances[] = { 100,   125,   150,   175,   200,   225,   237.8, 241.9};
        //double speeds[] =    { 27.39, 30.04, 32.68, 35.33, 37.98, 40.63, 46,    52};
        double speeds[] =    { 27.39, 30.04, 32.68, 34.33, 36.98, 39.63, 45,    52};

        if (distances.length != speeds.length) {
            System.err.println("Invalid speed/distance data");
            return;
        }


        double speed;
        if (distance >= distances[distances.length - 1]) {
            // Max distance == max speed
            speed = speeds[speeds.length - 1];
        } else if (distance <= distances[0]) {
            // Min distance == min speed
            speed = speeds[0];
        } else {
            // Distance is somewhere inside our chart
            // Find the first distance that meets or exceeds our target distance
            int i = 0;
            while (distances[i] < distance) {
                i++;
            }

            // Calculate distance and speed differences about our target
            double targetDiff = distances[i] - distance;
            double distanceDiff = distances[i] - distances[i - 1];
            double speedDiff = speeds[i] - speeds[i - 1];

            // Determine the ratio of differences
            double diffRatio = ((targetDiff) / (distanceDiff));

            // Base speed + (difference ratio * speed difference)
            speed = speeds[i-1] + (diffRatio * speedDiff);
        }
        
        this.setSpeed(speed);
    }

    public void start() {
        
        //shooter.set(-0.1);
        this.enable();
    }

    public void stop() {
        
        //System.out.println("stopped");
        this.disable();
        shooter.stopMotor();
    }
    public void disable(){
        //System.out.println("dissabled");
        super.disable();
    }
}
