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
    private ParticleAnalysisReport mainTarget=null;
    
    public void initDefaultCommand() {
    }
    
    /********getters**********/
    public boolean hasTarget(){
        
    }
    public ColorImage getImage(){
        return image;
    }
    public BinaryImage getThresholdHSLImage() {
        return thresholdHSLImage;
    }
    public void saveimage() throws NIVisionException{
        String pic="/Test_Capture";
        System.out.println("Saveing picture to"+pic+imagenumber);
        thresholdHSLImage.write("/Test_Capture"+imagenumber+++".png");
    } 
    
    
    
    /************initial scan**************/
    public void refreshImages() throws AxisCameraException, NIVisionException{
        
        flushImages();
        try{
        image= camera.getImage();
        }catch(Exception e){System.err.println("could not retrieve camera Threshold");}
        try{
            thresholdHSLImage=this.HSLThreshold();
        }catch (Exception e){System.err.print("threshold failed");}
    }
    private void flushImages() throws NIVisionException{
        targets=null;
        if(image!=null){
            image.free();
        }
        if(thresholdHSLImage!=null){
            thresholdHSLImage.free();
        }
    }
    
    private BinaryImage HSLThreshold() throws NIVisionException{
        if(image==null)return null;
        return image.thresholdHSL(RobotMap.HUE_LOW, RobotMap.HUE_HIGH, RobotMap.SAT_LOW, RobotMap.SAT_HIGH, RobotMap.LUM_LOW, RobotMap.LUM_HIGH);
    }
    
    
    
    /*************evaluations***********/
    
    private void IDTargets() throws NIVisionException{
        //targetDetected=thresholdHSLImage.getNumberParticles()!=0;
        thresholdHSLImage.removeSmallObjects(true, 2);
        ParticleAnalysisReport[] reports = thresholdHSLImage.getOrderedParticleAnalysisReports(4);
        for(int i=reports.length-1;i>=0;i--){
            if((reports[0].particleArea*RobotMap.TARGET_MIN_RATIO<reports[i].particleArea)){
                targets=subArray(reports,i);
                return;
            }
        }
        targets= null;//this shouldn't be possible
    }
    private ParticleAnalysisReport getTarget(){
        if(targets==null){
            try{
                IDTargets();
            }catch(Exception e){System.err.println("Couldn't ID target");}
        }
            return null;
    }
    
    
    //wrappers for important methods
    public double getAzimuth() throws NIVisionException{
        ParticleAnalysisReport target=null;
        return getAzimuth(target);
    }
    public double getTargetDistance() throws NIVisionException{
        ParticleAnalysisReport target =null;
        return getTargetDistance(target);
    }
    public double getTargetAngle(){
        ParticleAnalysisReport target =null;
        return getTargetAngle(target);
    }
    
    //important methods
    private double getAzimuth(ParticleAnalysisReport target) throws NIVisionException{
        if(target == null)return 1000;
        double mid=target.boundingRectLeft+target.boundingRectWidth/2.;
        return (mid/image.getWidth())*RobotMap.CAMERA_VA - RobotMap.CAMERA_VA*0.5;
    }
    private double getTargetDistance(ParticleAnalysisReport target) throws NIVisionException{
        double targetWidth = 1.0* target.boundingRectHeight * (RobotMap.TARGET_W/RobotMap.TARGET_H);
        double targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
        double Distance=(RobotMap.TARGET_W * 0.5) / Math.tan(Math.toRadians(targetViewingAngle*.5));
        return Distance; 
    }
    private double getTargetAngle(ParticleAnalysisReport target){
        return 0;
    }
    
    
    
    
    private ParticleAnalysisReport[] subArray(ParticleAnalysisReport[] array,int lastIndex){
        if(array.length<=lastIndex+1)throw new ArrayIndexOutOfBoundsException("wrong size for the Array!");
        ParticleAnalysisReport[] ret=new ParticleAnalysisReport[lastIndex+1];
        for(int i=0;i<=lastIndex;i++){
            ret[i]=array[i];
        }
        return ret;
    }
}
