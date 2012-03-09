/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.HSLImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.RefreshCameraImage;

/**
 *
 * @author Ingyram
 */
public class Camera extends Subsystem {
    private HSLImage image;
    private BinaryImage thresholdHSLImage;
    // Create and set up a camera instance 
    private AxisCamera camera = AxisCamera.getInstance();
    
    public Camera(){
        try {
            this.refreshImages();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
        } catch (NIVisionException ex) {
            ex.printStackTrace();
        } 
    }
    
    private class Point{
        public double x=0;
        public double y=0;
        public Point(double x, double y){
            this.x=x;
            this.y=y;
        }
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new RefreshCameraImage());
    }
    
    public void refreshImages() throws AxisCameraException, NIVisionException{
        image=(HSLImage) camera.getImage();
        thresholdHSLImage=this.HSLThreshold();
    }
    
    public void flushImages() throws NIVisionException{
        image.free();
        thresholdHSLImage.free();
    }
    
    public HSLImage getImage(){
        return image;
    }
    
    //Does an HSL threshold detection to find pixels of the target
    public BinaryImage HSLThreshold() throws NIVisionException{
        return image.thresholdHSL(RobotMap.HUE_LOW, RobotMap.HUE_HIGH, RobotMap.SAT_LOW, RobotMap.SAT_HIGH, RobotMap.LUM_LOW, RobotMap.LUM_HIGH);
    }
    
    //Does a particle analysis of a binary image of the target
    public ParticleAnalysisReport getTarget(BinaryImage image) throws NIVisionException{
        int numParticles = image.getNumberParticles();
        if(numParticles==0){
            return null;
        }
        if(numParticles>1){
            ParticleAnalysisReport[] reports = image.getOrderedParticleAnalysisReports();
            ParticleAnalysisReport largestParticle = reports[0];
            for(int i=0;i<reports.length;i++){
                if(reports[i].particleArea>largestParticle.particleArea){
                    largestParticle=reports[i];
                }
            }
            return largestParticle;
        }
        return image.getParticleAnalysisReport(0);
    }
    
    //Returns the angle to the target between -24 and +24 degrees.
    //For x, a positive number means the target is clockwise (right), while a negative means counterclockwise (left).
    //Returns 1000 if no target can be found
    //Also note that if only part of the target is in view, this will return the angle to the center of the partial target
    public double getAngleToTarget(ParticleAnalysisReport target) throws NIVisionException{
        if(target == null)return 1000;
        return (target.center_mass_x/image.getWidth())*RobotMap.CAMERA_VA - RobotMap.CAMERA_VA*0.5;
    }
    
    //Returns the distance from the camera to the target in inches
    public double getTargetDistance(ParticleAnalysisReport target) throws NIVisionException{
        double targetWidth = 1.0* target.boundingRectHeight * (RobotMap.TARGET_W/RobotMap.TARGET_H);
        double targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
        return (targetWidth * 0.5) / Math.tan(targetViewingAngle * 0.5);
    }
}