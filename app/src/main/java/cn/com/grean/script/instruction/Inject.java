package cn.com.grean.script.instruction;

import cn.com.grean.InjectionPump.InjectionPumpManipulator;
import cn.com.grean.RS232InjectionPump;
import cn.com.grean.script.LogListener;

/**
 * Created by weifeng on 2017/10/19.
 */

public class Inject implements Command{
    private CommandSerialPort com;

    public Inject(){
        com = RS232InjectionPump.getInstance();
    }
    @Override
    public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
        if (params.length>=3){
            if(params[1] <= 1000) {
                if (params[0] == 0) {
                    com.Send(InjectionPumpManipulator.getGo2PoseCmd(false, params[1]));
                } else {
                    com.Send(InjectionPumpManipulator.getGo2PoseCmd(true, params[1]));
                }
                return params[2];
            }else{
                return 0;
            }

        }else {
            return 0;
        }
    }

    @Override
    public boolean RepetitiveExecute() {
        return false;
    }

    @Override
    public void end(ChangeRepetitiveCommand change) {

    }
}
