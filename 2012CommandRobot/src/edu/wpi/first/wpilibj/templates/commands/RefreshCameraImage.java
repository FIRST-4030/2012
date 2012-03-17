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
        System.out.println("camera in use");
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
            System.out.println();
            camera.refreshImages();
            
            if(camera.getImage()!=null){
                System.out.println("starting targetreport");
                ParticleAnalysisReport[] target = camera.getTarget();
                System.out.println(target[0]);
                SmartDashboard.putString("Target", target[0].center_mass_x+","+target[0].center_mass_y);
                
                if(target[0]!=null){
                    SmartDashboard.putDouble("ANGLE TO TARGET", camera.getAngleToTarget(target[0]));
                    SmartDashboard.putDouble("DISTANCE TO TARGET", camera.getTargetDistance(target[0]));
                }
            }else{
                System.out.println("No Image");
            }
            
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }catch(NullPointerException e){
            System.err.println("WARNING: at some point the image wasn't proccessed");
            //e.printStackTrace();
        }catch(Exception e){
        System.err.println("unknown exception happen in camera(non essential so continuing)");
    }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
       /*try {
            //camera.flushImages();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        }*/
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        this.end();
    }
}
