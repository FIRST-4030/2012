package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadShooterEncoder;

public class ShooterEncoder extends Subsystem {

    private double lastTime = 0;
    private double rate = 0.0;
    private Counter encoder;
    DigitalInput a = new DigitalInput(RobotMap.ENCODER_CHANNEL_A);

    public ShooterEncoder() {
        encoder = new Counter(a);
        encoder.start();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadShooterEncoder());
    }

    public double getRate() {
        double time = (Utility.getFPGATime() / 1000000);
        if (time > lastTime + RobotMap.ENCODER_UPDATE_PERIOD) {
            rate = (double) encoder.get() / (time - lastTime);
            SmartDashboard.putDouble("Shooter Count", (double) encoder.get());
            encoder.reset();
            lastTime = time;
            SmartDashboard.putDouble("Shooter Time", time);
            SmartDashboard.putDouble("Shooter Last Time", lastTime);
            SmartDashboard.putDouble("Shooter Time Diff", time - lastTime);
        }
        SmartDashboard.putDouble("Shooter Rate", rate);
        SmartDashboard.putBoolean("Shooter A", a.get());
        return 0;
    }
}
