package cn.com.grean.InjectionPump;

import cn.com.grean.RS232InjectionPump;
import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/18.
 */

public class InjectionPumpManipulator {
    private CommandSerialPort com;
    private InjectionPumpManipulatorListener listener;
    public static final String initCmd ="/"+"12ZR"+"\r";
    public static final int InjectionPumpMaxPos=1000;
    public InjectionPumpManipulator(InjectionPumpManipulatorListener listener){
        this.listener = listener;
        com = RS232InjectionPump.getInstance();
    }


    public void initPump(){
        com.Send(initCmd.getBytes());
    }

    public void go2Pose(boolean valve,int pos){
        if(pos <=InjectionPumpMaxPos) {
            com.Send(getGo2PoseCmd(valve,pos));
        }
    }

    public static byte[] getGo2PoseCmd(boolean valve,int pos){
        String head = "/" + "1", cmd;
        if (valve) {
            cmd = "I";
        } else {
            cmd = "O";
        }
        cmd += "A" + String.valueOf(pos) + "R";
        head = head + String.valueOf(cmd.length());
        cmd = head + cmd + "\r";
        return cmd.getBytes();
    }
}
