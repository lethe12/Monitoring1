package cn.com.grean.script.algorithm;

/**
 * Created by weifeng on 2017/10/23.
 */

public interface MultiSampleComputer {
    Compute getComputer();
    void setBackGroundValue(float data);
    float calcResult(float measureValue,int num);
    float getComputeResult(int num);
}
