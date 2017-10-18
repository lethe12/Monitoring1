package cn.com.grean.script.instruction;

import cn.com.grean.RS232RobotArm;
import cn.com.grean.RobotArm.RobotArmManipulator;
import cn.com.grean.script.LogListener;

/**
 * Created by weifeng on 2017/10/18.
 */

public class PoseZeroCommand implements Command{
    @Override
    public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
        RS232RobotArm.getInstance().Send(RobotArmManipulator.homeCmd());
        return 0;
    }

    @Override
    public boolean RepetitiveExecute() {
        return false;
    }

    @Override
    public void end(ChangeRepetitiveCommand change) {

    }
}
