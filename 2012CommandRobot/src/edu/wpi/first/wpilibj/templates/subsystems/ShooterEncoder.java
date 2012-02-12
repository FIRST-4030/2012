/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.ReadGravity;
import edu.wpi.first.wpilibj.templates.commands.ReadShooterEncoder;

/**
 *
 * @author prog
 */
public class ShooterEncoder extends Subsystem
{
    private Encoder encoder;
    public ShooterEncoder(){
        encoder=new Encoder(RobotMap.ENCODER_CHANNEL_A,RobotMap.ENCODER_CHANNEL_B);
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand()
    {  
        setDefaultCommand(new ReadShooterEncoder());
    }
    public double getRate(){
        return encoder.getRate();
    }
    
    
}
