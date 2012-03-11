/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Ingyram
 */
public class RefreshCameraImage extends CommandBase {
    
    public RefreshCameraImage() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(camera);
        requires(ledring);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            ledring.on();
            camera.refreshImages();
            if(camera.getImage()!=null){
                //System.out.println("reporting");
                ParticleAnalysisReport target = camera.getTarget(camera.getThresholdHSLImage());
                //System.out.println("reported");
                if(target!=null){
                    SmartDashboard.putDouble("ANGLE TO TARGET", camera.getAngleToTarget(target));
                    SmartDashboard.putDouble("DISTANCE TO TARGET", camera.getTargetDistance(target));
                }
            }
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }catch(Exception E){
            System.out.println("WARNING ERROR, but stopping with this error is fucking retarded");
        }//ledring.off();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        try {
            camera.flushImages();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
