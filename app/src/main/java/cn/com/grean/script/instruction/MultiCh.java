package cn.com.grean.script.instruction;

import cn.com.grean.myApplication;
import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;
import cn.com.grean.script.algorithm.Compute;
import cn.com.grean.script.algorithm.MultiSampleComputer;

/**
 * Created by weifeng on 2017/10/23.
 */

public class MultiCh implements Command{
    @Override
    public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
        byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
        GeneralData data = (GeneralData) com.SyncSend(cmd);
        if(data!=null) {
            if (params.length == 3) {
                int num = params[0];
                if (params[2] == 0) {
                    if(data == null){
                        logListener.writeLog("MultiCH,x,x,1 通讯异常");
                        return 1;
                    }
                    myApplication.getInstance().getMultiSampleComputer().setBackGroundValue(data.getOne(params[1]-1));
                } else if (params[2] == 1) {
                    if(data == null){
                        logListener.writeLog("MultiCH,x,x,2 通讯异常");
                        return 1;
                    }
                    String stage = ScriptContent.getInstance().getScriptName();
                    MultiSampleComputer computer = myApplication.getInstance().getMultiSampleComputer();
                    ResultListener resultListener = ScriptContent.getInstance();
                    if (stage.equals("测量")) {
                        computer.calcResult(data.getOne(params[0] - 1),num);
                        resultListener.onResultsComplete(computer,num);
                    } else if (stage.equals("高点校准")) {
                        computer.getComputer().calibrationHigh(data.getOne(params[0] - 1), 0, 1);
                        resultListener.onCalibrationComplete(computer.getComputer().getCalibrationHighLogInfo());
                    } else if (stage.equals("低点校准")) {
                        computer.getComputer().calibrationLow(data.getOne(params[0] - 1), 0, 1);
                        resultListener.onCalibrationComplete(computer.getComputer().getCalibrationLowLogInfo());
                    } else {

                    }
                }
            }
        }

        return 1;
    }

    @Override
    public boolean RepetitiveExecute() {
        return false;
    }

    @Override
    public void end(ChangeRepetitiveCommand change) {
        change.onComplete(Command.NoChangeGhost);
    }
}
