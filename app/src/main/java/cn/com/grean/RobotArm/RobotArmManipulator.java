package cn.com.grean.RobotArm;


import cn.com.grean.RS232RobotArm;
import cn.com.grean.script.instruction.CommandSerialPort;
import cn.com.grean.tools;

/**
 * Created by weifeng on 2017/10/17.
 */

public class RobotArmManipulator {
    private RobotArmManipulatorListener listener;
    private boolean scanRun = false;
    private CommandSerialPort com;
    private RobotArmState state;
    private static final byte[] scanPos={(byte) 0xaa, (byte) 0xaa,0x02,0x0a,0x00, (byte) 0xf6};

    public RobotArmManipulator(RobotArmManipulatorListener listener){
        this.listener = listener;
        this.com = RS232RobotArm.getInstance();
    }

    public void startScan(){
        new ScanThread().start();
    }

    public void stopScan(){
        scanRun = false;
    }

    public boolean isScanRun(){
        return scanRun;
    }

    private class ScanThread extends Thread{
        @Override
        public void run() {
            String pos;
            scanRun = true;
            while (scanRun){
                state = (RobotArmState) com.SyncSend(scanPos);
                pos = "X=" + String.valueOf(state.getX())+"; Y="+String.valueOf(state.getY())+"; Z = "+String.valueOf(state.getZ());
                listener.showRealTimePos(pos);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            scanRun = false;
        }
    }

    public void jump2Pose(float x,float y,float z){
        com.Send(ptpJumpXyzCmd(x,y,z));
    }

    private static byte calcCmdSum(byte [] cmd ,int start,int end){
        byte sum = 0;
        for(int i = start;i<end;i++){
            sum+=cmd[i];
        }
        sum = (byte) ((sum^0xff)+1);
        return sum;
    }

    public static byte [] ptpJumpXyzCmd(float x,float y,float z){
        byte [] cmd = new byte[23];
        cmd[0] = (byte) 0xaa;
        cmd[1] = (byte) 0xaa;
        cmd[2] = 0x13;
        cmd[3] = 0x54;
        cmd[4] = 0x03;//(byte) 0xFF;
        cmd[5] = 0x00;
        byte[] pos = tools.float2byte(x);
        cmd[6] = pos[0];
        cmd[7] = pos[1];
        cmd[8] = pos[2];
        cmd[9] = pos[3];
        pos = tools.float2byte(y);
        cmd[10] = pos[0];
        cmd[11] = pos[1];
        cmd[12] = pos[2];
        cmd[13] = pos[3];
        pos = tools.float2byte(z);
        cmd[14] = pos[0];
        cmd[15] = pos[1];
        cmd[16] = pos[2];
        cmd[17] = pos[3];
        cmd[18] = 0x00;
        cmd[19] = 0x00;
        cmd[20] = 0x00;
        cmd[21] = 0x00;
        cmd[22] = calcCmdSum(cmd,3,21);
        return cmd;
    }


}
