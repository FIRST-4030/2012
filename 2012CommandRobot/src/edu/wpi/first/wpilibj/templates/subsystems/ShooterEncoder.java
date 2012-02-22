package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadShooterEncoder;

public class ShooterEncoder extends Subsystem {

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
        double rate = 1.0 / encoder.getPeriod();
        SmartDashboard.putDouble("Shooter Rate", rate);
        return rate;
    }
}
