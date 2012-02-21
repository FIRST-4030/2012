package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadShooterEncoder;

public class ShooterEncoder extends Subsystem {

    private Encoder encoder;
    DigitalInput a=new DigitalInput(RobotMap.ENCODER_CHANNEL_A);
    DigitalInput b=new DigitalInput(RobotMap.ENCODER_CHANNEL_B);

    public ShooterEncoder() {
        encoder = new Encoder(a, b);
        encoder.setDistancePerPulse(1);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ReadShooterEncoder());
    }

    public double getRate() {
        double rate = encoder.getRate();
        SmartDashboard.putDouble("Shooter Rate", rate);
        SmartDashboard.putDouble("Shooter Raw", encoder.getRaw());
        SmartDashboard.putBoolean("Channel A", a.get());
        SmartDashboard.putBoolean("Channel B", b.get());
        SmartDashboard.putDouble("Get_Period", encoder.getPeriod());
        return rate;
    }
}
