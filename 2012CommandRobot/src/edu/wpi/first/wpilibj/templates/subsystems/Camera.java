/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.RefreshCameraImage;

/**
 *
 * @author Ingyram
 */
public class Camera extends Subsystem {
    private ColorImage image;
    private BinaryImage thresholdHSLImage;
    // Create and set up a camera instance 
    private AxisCamera camera = AxisCamera.getInstance("10.40.30.11");

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
        //System.out.println("flshing images");
        flushImages();
        //System.out.println("down the drain");
        SmartDashboard.putBoolean("is camera derp", camera==null);
        try{
            //System.out.println("getting image");
            image= camera.getImage();
            //System.out.println("got image");
            thresholdHSLImage=this.HSLThreshold();
            //thresholdHSLImage.
        }catch(Exception e){System.out.println("I AM DER{PER{EPRESPSREP");}
    }

    public BinaryImage getThresholdHSLImage() {
        return thresholdHSLImage;
    }
    
    public void flushImages() throws NIVisionException{
        if(image!=null){
            image.free();
        }
        if(thresholdHSLImage!=null){
            thresholdHSLImage.free();
        }
    }
    public void saveimage() throws NIVisionException{
        System.out.println("savine der pictuers");
        thresholdHSLImage.write("/DESU_"+imagenumber+++".png");
    }
    
    public ColorImage getImage(){
        return image;
    }
    int imagenumber=0;
    //Does an HSL threshold detection to find pixels of the target
    public BinaryImage HSLThreshold() throws NIVisionException{
        if(image==null)return null;
        //System.out.println("got to hsl");
        BinaryImage ret= image.thresholdHSL(RobotMap.HUE_LOW, RobotMap.HUE_HIGH, RobotMap.SAT_LOW, RobotMap.SAT_HIGH, RobotMap.LUM_LOW, RobotMap.LUM_HIGH);
        
        return ret;
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
        double mid=target.boundingRectLeft+target.boundingRectWidth/2.;
        return (mid/image.getWidth())*RobotMap.CAMERA_VA - RobotMap.CAMERA_VA*0.5;
    }
    
    //Returns the distance from the camera to the target in inches
    public double getTargetDistance(ParticleAnalysisReport target) throws NIVisionException{
        double targetWidth = 1.0* target.boundingRectHeight * (RobotMap.TARGET_W/RobotMap.TARGET_H);
        double targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
        return (targetWidth * 0.5) / Math.tan(targetViewingAngle * 0.5) * 4.0;
    }
}