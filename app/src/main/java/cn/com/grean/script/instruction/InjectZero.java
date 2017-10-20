package cn.com.grean.script.instruction;

import cn.com.grean.InjectionPump.InjectionPumpManipulator;
import cn.com.grean.RS232InjectionPump;
import cn.com.grean.script.LogListener;

/**
 * Created by weifeng on 2017/10/19.
 */

public class InjectZero implements Command{
    @Override
    public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
        RS232InjectionPump.getInstance().Send(InjectionPumpManipulator.initCmd.getBytes());
        return params[0];
    }

    @Override
    public boolean RepetitiveExecute() {
        return false;
    }

    @Override
    public void end(ChangeRepetitiveCommand change) {

    }
}
