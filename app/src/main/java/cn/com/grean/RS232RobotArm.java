package cn.com.grean;

import android.animation.ObjectAnimator;
import android.util.Log;

import cn.com.grean.RobotArm.RobotArmState;
import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/17.
 */

public class RS232RobotArm extends SerialPortCommunication implements CommandSerialPort {
    private static final String tag = "RS232RobotArm";
    private static RS232RobotArm instance = new RS232RobotArm();
    private RobotArmState state = new RobotArmState();
    private Object syncData;

    public static RS232RobotArm getInstance() {
        return instance;
    }

    private RS232RobotArm(){
        super(1, 8, 115200, 1, 'n');//默认串口0 115200 8  无  1

    }

    @Override
    protected Boolean handleBuffer() {
        return true;
    }

    @Override
    protected void CommunicationProtocal(byte[] rec, int len) {
        if(checkSum(rec,len)){
            Log.d(tag,"sum check right");
            switch (rec[3]){
                case 0x0a:
                    float pos = tools.getFloatReversedOder(rec,5);
                    state.setX(pos);
                    pos = tools.getFloatReversedOder(rec,9);
                    state.setY(pos);
                    pos = tools.getFloatReversedOder(rec,13);
                    state.setZ(pos);
                    pos = tools.getFloatReversedOder(rec,17);
                    state.setR(pos);
                    pos = tools.getFloatReversedOder(rec,21);
                    state.setJointAngle(0,pos);
                    pos = tools.getFloatReversedOder(rec,25);
                    state.setJointAngle(1,pos);
                    pos = tools.getFloatReversedOder(rec,29);
                    state.setJointAngle(2,pos);
                    pos = tools.getFloatReversedOder(rec,33);
                    state.setJointAngle(3,pos);
                    syncData = state;
                    break;
                case 0x54:

                    break;
                default:

                    break;
            }


        }else{
            Log.d(tag,"sum check error");
        }
    }

    @Override
    protected void AsynchronousCommunicationProtocal(byte[] rec, int len) {

    }

    public RobotArmState getState() {
        return state;
    }

    @Override
    public void Send(byte[] data) {
        sendBuff.add(data);
    }

    @Override
    public Object SyncSend(byte[] data) {
        syncData = null;
        int times=0;
        Log.d(tag,"发送");
        sendBuff.add(data);
        while ((times <10)&&(syncData == null)) {
        //while ((times <10)) {
            try {
                times++;
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
        //String string = (String) syncData;

        return syncData;
        //return null;
    }

    private boolean checkSum(byte [] buff,int l){
        if(l<6){
            return false;
        }

        if(buff[2]!=(l-4)){
            return false;
        }

        int sum = 0;
        for(int i = 3;i<l;i++){
            sum += buff[i];
        }
        if((0x00ff & sum)!=0){
            return false;
        }else{
            return true;
        }
    }
}
