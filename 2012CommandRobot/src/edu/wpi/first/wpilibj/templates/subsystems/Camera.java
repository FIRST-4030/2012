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
    private ColorImage image=null;
    private BinaryImage thresholdHSLImage=null;
    private int imagenumber=0;//keeps track of images being saved
    // Create and set up a camera instance 
    private AxisCamera camera = AxisCamera.getInstance();
    private Target[] targets=null;
    private Target mainTarget=null;

   
    
    private class Target{
        private double height;
        private double width;
        private double x;
        private double y;
        private double centerX;
        private double centerY;

        private Target(ParticleAnalysisReport report)
        {
            height=report.boundingRectHeight;
            width=report.boundingRectWidth;
            x=report.boundingRectLeft;
            y=report.boundingRectTop;
            centerX=x+width/2;
            centerY=y+height/2;
        }

        public double getHeight()
        {
            return height;
        }

        public double getCenterX()
        {
            return centerX;
        }

        public double getCenterY()
        {
            return centerY;
        }

        public double getWidth()
        {
            return width;
        }

        public double getX()
        {
            return x;
        }

        public double getY()
        {
            return y;
        }
        
    }
    
    
    public void initDefaultCommand() {
    }
    
    /********getters**********/
    public boolean hasTarget(){
        return targets!=null;
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
        }catch(Exception e){System.err.println("could not retrieve camera Image");}
        try{
            thresholdHSLImage=this.HSLThreshold();
        }catch (Exception e){System.err.print("threshold failed");}
        try{
            IDTargets();
        }catch(Exception e){System.err.println("Couldn't ID target");}
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
    
    private void IDTargets() throws NIVisionException{
        //thresholdHSLImage.removeSmallObjects(true, 2);
        ParticleAnalysisReport[] reports = thresholdHSLImage.getOrderedParticleAnalysisReports();
        if (reports[0].particleArea <600){
         targets=null;
         SmartDashboard.putInt("numTargets", 0);
         return;
        }
        
        
        //assuming its organized by size remove everything below a certian size
        for(int i=reports.length-1;i>=0;i--){
            if((reports[0].particleArea*RobotMap.TARGET_MIN_RATIO<reports[i].particleArea)){
                targets=refineTargets(reports,i);
                SmartDashboard.putInt("numTargets", targets.length);
                SmartDashboard.putDouble("target size", reports[0].particleArea);
                return;
            }
        }
        SmartDashboard.putInt("numTargets", 0);
        targets= null;//this shouldn't be possible
    }
    
    
    /*************evaluations***********/
    
    
    private Target getTarget(){
        double prop=targets[0].getHeight()*.75;
        Target l,r,u,d;
        l=r=u=d=targets[0];
        for(int i=1;i<targets.length;i++){
            if(targets[i].centerX < l.centerX)l=targets[i];
            if(targets[i].centerX > r.centerX)r=targets[i];
            if(targets[i].centerY < u.centerY)u=targets[i];
            if(targets[i].centerY > d.centerY)d=targets[i];
        }
        SmartDashboard.putInt("TARGETS VISIBLE", targets.length);
        switch(targets.length){
            case 3:
                if(u.getCenterY()<r.getCenterY()-prop||u.getCenterY()<l.getCenterY()-prop)return u;
                return d;
            case 2:
                return u;
            case 4:
            case 1:
                return u;
    }
        
        
            return null;
    }
    
    
    //wrappers for important methods
    
    public double getAzimuth() throws NIVisionException{
        return getAzimuth(getTarget());
    }
    public double getTargetDistance() throws NIVisionException{
        return getTargetDistance(targets[0]);
    }
    public double getTargetAngle(){
        return getTargetAngle(getTarget());
    }
    
    //important methods
    private double getAzimuth(Target target) throws NIVisionException{
        if(target == null)return 1000;
        //double mid=target.boundingRectLeft+target.boundingRectWidth/2.;
        return (target.getCenterX()/image.getWidth())*RobotMap.CAMERA_VA - RobotMap.CAMERA_VA*0.5;
    }
    private double getTargetDistance(Target target) throws NIVisionException{
        double targetWidth = 1.0* target.getHeight() * (RobotMap.TARGET_W/RobotMap.TARGET_H);
        double targetViewingAngle = 1.0* (RobotMap.CAMERA_VA) * targetWidth/image.getWidth();
        double Distance=(RobotMap.TARGET_W * 0.5) / Math.tan(Math.toRadians(targetViewingAngle*.5));
        return Distance; 
    }
    private double getTargetAngle(Target target){
        return Math.toDegrees(com.sun.squawk.util.MathUtils.acos(target.width/(target.height*(RobotMap.TARGET_W/RobotMap.TARGET_H))));
    }
    
    
    private Target[] refineTargets(ParticleAnalysisReport[] array,int lastIndex){
        if(array.length<lastIndex+1)throw new ArrayIndexOutOfBoundsException("wrong size for the Array!");
        Target[] ret=new Target[lastIndex+1];
        for(int i=0;i<=lastIndex;i++){
            ret[i]=new Target(array[i]);
        }
        return ret;
    }
}
