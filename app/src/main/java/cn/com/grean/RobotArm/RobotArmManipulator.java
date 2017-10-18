package cn.com.grean.RobotArm;


import android.util.Log;

import cn.com.grean.LocalConfig;
import cn.com.grean.RS232RobotArm;
import cn.com.grean.myApplication;
import cn.com.grean.script.instruction.CommandSerialPort;
import cn.com.grean.tools;

/**
 * Created by weifeng on 2017/10/17.
 */

public class RobotArmManipulator {
    private static final String tag = "RobotArmManipulator";
    public static final int PoseSize = 20;
    private RobotArmManipulatorListener listener;
    private boolean scanRun = false;
    private CommandSerialPort com;
    private RobotArmState state;
    private float nowX,nowY,nowZ;
    public static final byte[] scanPos={(byte) 0xaa, (byte) 0xaa,0x02,0x0a,0x00, (byte) 0xf6};
    private LocalConfig config;

    public RobotArmManipulator(RobotArmManipulatorListener listener){
        this.listener = listener;
        this.com = RS232RobotArm.getInstance();
        state = RS232RobotArm.getInstance().getState();
        config = myApplication.getInstance();
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
            RobotArmState state;
            while (scanRun){
                state = (RobotArmState) com.SyncSend(scanPos);
                if(state!=null) {
                    state.calcBias();
                    nowX = state.getX();
                    nowY = state.getY();
                    nowZ = state.getZ();
                    pos = "X=" + String.valueOf(nowX) + "; Y=" + String.valueOf(nowY) + "; Z = " + String.valueOf(nowZ)+"; bias = "+String.valueOf(state.getBias());
                    listener.showRealTimePos(pos);
                }
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
        state.setTargetX(x);
        state.setTargetY(y);
        state.setTargetZ(z);
        com.Send(ptpJumpXyzCmd(x,y,z));
    }

    public void jump2Home(){
        com.Send(homeCmd());
    }

    public void setHomeParams(){
        com.Send(homeParamsCmd(nowX,nowY,nowZ));
    }

    public void savePose(String string){
        int num = Integer.valueOf(string);
        if((num <= PoseSize)&&(num>0)){
            String name = "Pose"+String.valueOf(num);
            String value = String.valueOf(nowX)+","+String.valueOf(nowY)+","+String.valueOf(nowZ);
            config.putConfig(name,value);
            listener.showPose(num,name+"("+value+")");
            RobotPose pose= new RobotPose(nowX,nowY,nowZ);
            state.addPose(num-1,pose);
        }
    }

    private static byte calcCmdSum(byte [] cmd ,int start,int end){
        byte sum = 0;
        for(int i = start;i<end;i++){
            sum+=cmd[i];
        }
        sum = (byte) ((sum^0xff)+1);
        return sum;
    }

    public String[] getLocalPoses(){
        String [] strings = new String[PoseSize];
        String name,value;
        for (int i=0;i<PoseSize;i++){
            name = "Pose"+String.valueOf(i+1);
            value = config.getConfigString(name);
            strings[i] = name+"("+value+")";
        }
        return strings;
    }

    public void go2Pose(int num){
        String name = "Pose"+String.valueOf(num);
        String value = config.getConfigString(name);
        if(!value.equals("")) {
            float [] pos = getString2Pose(value);
            state.setTargetX(pos[0]);
            state.setTargetY(pos[1]);
            state.setTargetZ(pos[2]);
            com.Send(ptpJumpXyzCmd(pos[0],pos[1],pos[2]));
        }
    }


    private static float[] getString2Pose(String string){
        float[] pose = new float[3];
        String[] strings = string.split(",");
        for(int i=0;i<strings.length;i++){
          pose[i] = Float.valueOf(strings[i]);
        }
        return pose;
    }

    private static void insertPose(byte [] cmd,int index,float x,float y, float z,float r){
        int i=index;
        byte[] pose = tools.float2byte(x);
        cmd[i++] = pose[0];
        cmd[i++] = pose[1];
        cmd[i++] = pose[2];
        cmd[i++] = pose[3];
        pose = tools.float2byte(y);
        cmd[i++] = pose[0];
        cmd[i++] = pose[1];
        cmd[i++] = pose[2];
        cmd[i++] = pose[3];
        pose = tools.float2byte(z);
        cmd[i++] = pose[0];
        cmd[i++] = pose[1];
        cmd[i++] = pose[2];
        cmd[i++] = pose[3];
        pose = tools.float2byte(r);
        cmd[i++] = pose[0];
        cmd[i++] = pose[1];
        cmd[i++] = pose[2];
        cmd[i++] = pose[3];
    }

    private static void insertHead(byte[] cmd,byte id,byte ctrl){
        cmd[0] = (byte) 0xaa;
        cmd[1] = (byte) 0xaa;
        cmd[2] = (byte) (cmd.length-4);
        cmd[3] = id;
        cmd[4] = ctrl;
    }

    private static byte [] homeParamsCmd(float x,float y,float z){
        byte[]cmd= new byte[22];
        insertHead(cmd, (byte) 30, (byte) 0x03);
        insertPose(cmd,5,x,y,z,0f);
        cmd[21] = calcCmdSum(cmd,3,20);
        return cmd;
    }

    public static byte [] homeCmd(){
        byte [] cmd = new byte[10];
        cmd[0] = (byte) 0xaa;
        cmd[1] = (byte) 0xaa;
        cmd[2] = 0x06;
        cmd[3] = 0x1F;
        cmd[4] = 0x03;
        cmd[5] = 0x00;
        cmd[6] = 0x00;
        cmd[7] = 0x00;
        cmd[8] = 0x00;
        cmd[9] = calcCmdSum(cmd,3,8);
        return cmd;
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
