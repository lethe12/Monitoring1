package cn.com.grean.InjectionPump;

import cn.com.grean.RS232InjectionPump;
import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/18.
 */

public class InjectionPumpManipulator {
    private CommandSerialPort com;
    private InjectionPumpManipulatorListener listener;
    private static final String initCmd ="/"+"1Z/n";
    public InjectionPumpManipulator(InjectionPumpManipulatorListener listener){
        this.listener = listener;
        com = RS232InjectionPump.getInstance();
    }


    public void initPump(){
        com.Send(initCmd.getBytes());
    }

    public void go2Pose(boolean valve,int pos){
        String cmd="/";
        if(valve){
            cmd+="I";
        }else{
            cmd+="O";
        }
        cmd+="A"+String.valueOf(pos)+"/n"+"R";

        com.Send(cmd.getBytes());
    }
}
