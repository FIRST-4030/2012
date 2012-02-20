package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.ReadDistance;

public class Distance extends Subsystem {

    private AnalogChannel range;
    edu.wpi.first.wpilibj.Ultrasonic ultra;
    
    public Distance(){
        
        range = new AnalogChannel(RobotMap.RANGEFINDER);
       //ultra =new Ultrasonic();
        //range.initAccumulator();
        
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new ReadDistance());
    }

  
    
    public int readDistance() {
        //range.
        SmartDashboard.putDouble("raw range", range.getAverageValue());
        double voltsPerInch = CommandBase.globalState.getVin() / 512.0;
        int distance = (int)(range.getAverageVoltage() * voltsPerInch);
        SmartDashboard.putDouble("voltsPerInch", voltsPerInch);
        SmartDashboard.putInt("Distance", distance);
        return distance;
    }
}
