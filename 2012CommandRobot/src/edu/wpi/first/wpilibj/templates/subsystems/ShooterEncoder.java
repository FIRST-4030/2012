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
    private long lastCount = 0;
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
        double time = Utility.getFPGATime();
        long count = encoder.get();
        double rate = (count - lastCount) / ((time - lastTime) / 1000000);

        SmartDashboard.putDouble("Shooter Rate", rate);
        SmartDashboard.putInt("Shooter Count", (int) count);

        lastTime = time;
        lastCount = count;
        return rate;
    }
}
