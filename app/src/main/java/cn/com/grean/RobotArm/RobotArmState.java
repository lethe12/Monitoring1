package cn.com.grean.RobotArm;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import cn.com.grean.LocalConfig;
import cn.com.grean.myApplication;

/**
 * 机械臂状态类
 * Created by weifeng on 2017/10/17.
 */

public class RobotArmState {
    private float x,y,z,r,bias,targetX,targetY,targetZ;
    private ArrayList<RobotPose> poses = new ArrayList<RobotPose>();
    private float[] jointAngle = new float[4];

    public int loadPoses(LocalConfig config){
        String name,value;
        String[] strings;
        int times=0;
        poses.clear();
        for(int i=0;i<RobotArmManipulator.PoseSize;i++){
            name = "Pose"+String.valueOf(i+1);
            value=config.getConfigString(name);
            if(!value.equals("")) {
                strings = value.split(",");
                if (strings.length == 3) {
                    RobotPose pose = new RobotPose(Float.valueOf(strings[0]), Float.valueOf(strings[1]), Float.valueOf(strings[2]));
                    poses.add(pose);
                    times++;
                }
            }else{
                RobotPose pose = new RobotPose(RobotArmManipulator.DefaultX, RobotArmManipulator.DefaultY, RobotArmManipulator.DefaultZ);
                poses.add(pose);
            }
        }
        return times;
    }

    public ArrayList<RobotPose> getPoses() {
        if(poses.size() == 0){
            loadPoses(myApplication.getInstance());
        }
        return poses;
    }

    public void addPose(int num,RobotPose pose){
        if(poses.isEmpty()){
            loadPoses(myApplication.getInstance());
        }
        poses.add(num,pose);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getJointAngle(int index) {
        if(index < 4) {
            return jointAngle[index];
        }else{
            return 0;
        }
    }

    public void setJointAngle(int index,float angle) {
        if(index <4) {
            this.jointAngle[index] = angle;
        }
    }

    public float calcBias(){
        double sum = (this.x-targetX)*(this.x-targetX);
        sum += (this.y-targetY)*(this.y-targetY);
        sum += (this.z-targetZ)*(this.z-targetZ);
        bias = (float) Math.sqrt(sum);
        return bias;
    }

    public float getBias() {
        return bias;
    }

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public float getTargetZ() {
        return targetZ;
    }

    public void setTargetZ(float targetZ) {
        this.targetZ = targetZ;
    }
}
