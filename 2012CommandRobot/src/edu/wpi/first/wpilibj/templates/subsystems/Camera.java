/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import com.sun.squawk.util.Arrays;
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
    private int imagenumber=0;//keeps track of images being saved
    // Create and set up a camera instance 
    private AxisCamera camera = AxisCamera.getInstance("10.40.30.11");
    
    private ParticleAnalysisReport[] targets;
    private boolean targetDetected=false;
    

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
        //setDefaultCommand(new RefreshCameraImage());
    }
    public void refreshImages() throws AxisCameraException, NIVisionException{
        System.out.println("memory colection");
        flushImages();
        System.out.println("getting image");
        image= camera.getImage();
        
        System.out.println("getting threshold :"+image==null);
        try{
        thresholdHSLImage=this.HSLThreshold();
        }catch (Exception e){
            System.out.print("threshold failed");
        }
    }
    public BinaryImage getThresholdHSLImage() {
        return thresholdHSLImage;
    }
    private void flushImages() throws NIVisionException{
        targets=null;
        targetDetected=false;
        if(image!=null){
            image.free();
        }
        if(thresholdHSLImage!=null){
            thresholdHSLImage.free();
        }
    }
    public void saveimage() throws NIVisionException{
        String pic="/Test_Capture";
        System.out.println("Saveing picture to"+pic+imagenumber);
        thresholdHSLImage.write("/Test_Capture"+imagenumber+++".png");
    } 
    public ColorImage getImage(){
        return image;
    }
    //Does an HSL threshold detection to find pixels of the target
    private BinaryImage HSLThreshold() throws NIVisionException{
        if(image==null)return null;
        return image.thresholdHSL(RobotMap.HUE_LOW, RobotMap.HUE_HIGH, RobotMap.SAT_LOW, RobotMap.SAT_HIGH, RobotMap.LUM_LOW, RobotMap.LUM_HIGH);
    }
    public void IDTargets() throws NIVisionException{
        targetDetected=thresholdHSLImage.getNumberParticles()!=0;
        thresholdHSLImage.removeSmallObjects(true, 2);
        ParticleAnalysisReport[] reports = thresholdHSLImage.getOrderedParticleAnalysisReports(4);
        for(int i=reports.length-1;i>=0;i--){
            System.out.println("testing: "+i);
            if((reports[0].particleArea*RobotMap.TARGET_MIN_RATIO<reports[i].particleArea)){
                System.out.println(i+" is the last one that fits");
                targets=subArray(reports,i);
                return;
            }else{
                System.out.println(i+"was too small");
            }
        }
        System.out.println(thresholdHSLImage.getNumberParticles());
        targets= null;//this shouldn't be possible
    }
    
    
    //Does a particle analysis of a binary image of the target
    public ParticleAnalysisReport[] getTarget() throws NIVisionException{
        
        IDTargets();
        return targets;
                /*
        int numParticles = image.getNumberParticles();
        if(numParticles==0){
            return null;
        }
        if(numParticles>1){
            //image.ge
            ParticleAnalysisReport[] reports = image.getOrderedParticleAnalysisReports();
            ParticleAnalysisReport largestParticle = reports[0];
            for(int i=0;i<reports.length;i++){
                if(reports[i].particleArea>largestParticle.particleArea){
                    largestParticle=reports[i];
                }
            }
            return largestParticle;
        }
        return image.getParticleAnalysisReport(0);*/
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
    public double getTargetDistance() throws NIVisionException{
        
        
        double targetWidth = 1.0* targets[0].boundingRectHeight * (RobotMap.TARGET_W/RobotMap.TARGET_H);
        double targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
        double Distance=(RobotMap.TARGET_W * 0.5) / Math.tan(Math.toRadians(targetViewingAngle*.5));
        /*if(Distance>160){
            SmartDashboard.getBoolean("using large target", true);
            double bottom=-5000,top=5000;
            for(int i=targets.length-1;i>=0;i--){
                if(targets[i].boundingRectTop<top)top=targets[i].boundingRectTop;
                if(targets[i].boundingRectTop+targets[i].boundingRectHeight>bottom)bottom=targets[i].boundingRectTop+targets[i].boundingRectHeight;
                targetWidth = 1.0* bottom-top * (RobotMap.TARGET_W/RobotMap.TARGET_H);
                targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
                Distance=(RobotMap.TOP_TO_BOTTOM_H*(RobotMap.TARGET_W/RobotMap.TARGET_H) * 0.5) / Math.tan(Math.toRadians(targetViewingAngle*.5));
                Distance/=4.90;
            }
        }else SmartDashboard.getBoolean("using large target", false);
        */
        return Distance; 
    }/*
    public double getdist(ParticleAnalysisReport target) throws NIVisionException{
        double width=target.boundingRectWidth/2;
        double angle=(RobotMap.CAMERA_VA*width/image.getWidth())/2;
        SmartDashboard.putDouble("ANGLE OF TARG", angle);
        
        double sideAngle=(90-angle);
        SmartDashboard.putDouble("SIDEANGLE", sideAngle);
        
        double ret=(RobotMap.TARGET_W/Math.sin(Math.toRadians(angle)))*Math.sin(Math.toRadians(sideAngle));
        return ret/3.93;
    }
    */
    private ParticleAnalysisReport[] subArray(ParticleAnalysisReport[] array,int lastIndex){
        if(array.length<=lastIndex+1)throw new ArrayIndexOutOfBoundsException();
        System.out.println("shortening array");
        ParticleAnalysisReport[] ret=new ParticleAnalysisReport[lastIndex+1];
        System.out.println("array is now of length "+ret.length);
        for(int i=0;i<=lastIndex;i++){
            ret[i]=array[i];
        }
        return ret;
    }
}
