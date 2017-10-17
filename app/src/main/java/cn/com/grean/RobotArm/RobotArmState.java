package cn.com.grean.RobotArm;

/**
 * 机械臂状态类
 * Created by weifeng on 2017/10/17.
 */

public class RobotArmState {
    private float x,y,z,r;
    private float[] jointAngle = new float[4];

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
}
