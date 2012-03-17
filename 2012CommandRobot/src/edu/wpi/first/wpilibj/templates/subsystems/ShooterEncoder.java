package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadShooterEncoder;

public class ShooterEncoder extends Subsystem {

    private int updates = 0;
    private double rate = 0;
    private long lastTime = 0;
    private long lastCount = 0;
    private Counter encoder;
    DigitalInput a = new DigitalInput(RobotMap.ENCODER);

    public ShooterEncoder() {
        encoder = new Counter(a);
        encoder.start();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadShooterEncoder());
    }

    public double getRate() {
        long time = Utility.getFPGATime();
        if (time > lastTime + RobotMap.ENCODER_INTERVAL) {
            long count = encoder.get();
            // 1000000.0 is an arbitrary constant to make the rate an integer
            rate = (count - lastCount) / ((double) (time - lastTime) / 1000000.0);

            SmartDashboard.putDouble("Shooter Rate", rate);
            SmartDashboard.putInt("Shooter Count", (int) count);
            SmartDashboard.putInt("Shooter Updates", updates++);

            lastTime = time;
            lastCount = count;
        }
        return rate;
    }
}
