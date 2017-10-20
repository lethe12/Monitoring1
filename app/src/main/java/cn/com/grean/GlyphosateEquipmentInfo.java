package cn.com.grean;

import java.text.DecimalFormat;

import cn.com.grean.script.instruction.CommandSerialPort;

/**
 * Created by weifeng on 2017/10/17.
 */

public class GlyphosateEquipmentInfo implements EquipmentInfo{
    private final static String tag = "GlyphosateEquipmentInfo";
    private EquipmentData data  = null;
    private final static String unit = "μg/L";
    private final static boolean [] pump = {true,true,true,true};
    private final static boolean [] valve = {true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true,true,true,
            true,true,true};
    private final static boolean [] vd = {true,false,false,false,false};
    private final static String [] vdOn = {"光源开","twoOn","ThreeOn","FourOn","FiveOn"};
    private final static String [] vdOff = {"光源关","TwoOff","ThreeOff","FourOff","FiveOff"};
    //private final static String [] virtualDevicesStrings = {"光源","加热器","浊度补偿系数"};
    private final static String [] virtualDevicesStrings = {"None"};
    private final static String [] ranges={"1样品","2样品","4样品","8样品"};
    private final static byte [] vdNum = {4,6,10,11,12};//虚拟设备编号

    public GlyphosateEquipmentInfo(){

    }

    @Override
    public EquipmentData getEquipmentData() {
        if (data==null) {
            data = new EquipmentData(pump, valve, vd, vdOn, vdOff,vdNum);
            data.setHasInjectionPump(true);
            data.setHasRobotArm(true);
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
    public String getUnit() {
        return unit;
    }

    @Override
    public int getSampleNumber(int type) {
        switch (type){
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 4;
            case 3:
                return 8;
            default:
                return 1;
        }
    }

    @Override
    public String getTag() {
        return "样品";
    }


}
