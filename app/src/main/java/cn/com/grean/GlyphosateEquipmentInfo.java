package cn.com.grean;

import java.text.DecimalFormat;

import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/17.
 */

public class GlyphosateEquipmentInfo implements EquipmentInfo{
    private final static String tag = "GlyphosateEquipmentInfo";
    private EquipmentData data  = null;
    private final static boolean [] pump = {true,true,true,true};
    private final static boolean [] valve = {true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true};
    private final static boolean [] vd = {true,true,false,false,false};
    private final static String [] vdOn = {"光源开","消解开","ThreeOn","FourOn","FiveOn"};
    private final static String [] vdOff = {"光源关","消解关","ThreeOff","FourOff","FiveOff"};
    //private final static String [] virtualDevicesStrings = {"光源","加热器","浊度补偿系数"};
    private final static String [] virtualDevicesStrings = {"None"};
    private final static String [] ranges={"2mg/L","5mg/L","10mg/L"};
    private final static byte [] vdNum = {4,6,10,11,12};//虚拟设备编号

    public GlyphosateEquipmentInfo(){

    }

    @Override
    public EquipmentData getEquipmentData() {
        if (data==null) {
            data = new EquipmentData(pump, valve, vd, vdOn, vdOff,vdNum);
        }
        return data;
    }

    @Override
    public String[] getVirtualDevicesString() {
        return virtualDevicesStrings;
    }

    @Override
    public void setVirtualDevices(int pos, String params, CommandSerialPort com) {

    }

    @Override
    public byte[] getEnableVirtualDevicesCmd() {
        return new byte[0];
    }

    @Override
    public String getResult(float result) {
        DecimalFormat fnum = new DecimalFormat("##0.000"); //保留小数点后3位
        return fnum.format(result);
    }

    @Override
    public String[] getDevicesRangStrings() {
        return ranges;
    }

    @Override
    public String getVirtualDevices(int pos, CommandSerialPort com) {
        return "None";
    }

    @Override
    public boolean hasRobotArm() {
        return true;
    }

    @Override
    public boolean hasInjectionPump() {
        return true;
    }
}
