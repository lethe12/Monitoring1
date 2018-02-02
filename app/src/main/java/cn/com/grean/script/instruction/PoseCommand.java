package cn.com.grean.script.instruction;

import android.util.Log;

import cn.com.grean.RS232RobotArm;
import cn.com.grean.RobotArm.RobotArmManipulator;
import cn.com.grean.RobotArm.RobotArmState;
import cn.com.grean.RobotArm.RobotPose;
import cn.com.grean.script.LogListener;

/**
 * Created by weifeng on 2017/10/18.
 */

public class PoseCommand implements Command{
    private static final String tag = "PoseCommand";
    private CommandSerialPort com;
    private RobotArmState state,tempState;
    private RobotPose pose;
    int times;

    public PoseCommand(){
        com = RS232RobotArm.getInstance();
        state = RS232RobotArm.getInstance().getState();
    }

    @Override
    public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
        if(params.length >=2) {
            if((params[0] > 0)&&(params[0] < 21))
            pose = state.getPoses().get(params[0]-1);
            state.setTargetX(pose.getX());
            state.setTargetY(pose.getY());
            state.setTargetZ(pose.getZ());
            this.com.Send(RobotArmManipulator.ptpJumpXyzCmd(pose.getX(), pose.getY(), pose.getZ()));
            times = 0;
            return params[1];
        }else{
            return 0;
        }
    }

    @Override
    public boolean RepetitiveExecute() {
        tempState = (RobotArmState) com.SyncSend(RobotArmManipulator.scanPos);
        if(tempState!=null) {
            tempState.calcBias();
            Log.d(tag,"位置误差"+String.valueOf(tempState.getBias()));
            if (tempState.getBias()<0.1f){//到达位置跳出循环
                Log.d(tag,"到达位置");
                return true;
            }
        }
        times++;
        if(times > 99){
            Log.d(tag,"超时跳出循环");
            return true;
        }
        return false;
    }

    @Override
    public void end(ChangeRepetitiveCommand change) {

    }
}
