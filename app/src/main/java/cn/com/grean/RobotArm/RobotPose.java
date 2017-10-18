package cn.com.grean.RobotArm;

/**
 * Created by weifeng on 2017/10/18.
 */

public class RobotPose {
    float x,y,z;

    public RobotPose(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

}
