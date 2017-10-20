package cn.com.grean;

import java.text.DecimalFormat;

import android.util.Log;

import cn.com.grean.script.instruction.CommandSerialPort;

public class TnEquipmentInfo implements EquipmentInfo{
	@SuppressWarnings("unused")
	private final static String tag = "TnEquipmentInfo";
	private final static String unit = "mg/L";
	private EquipmentData data  = null;
	private final static boolean [] pump = {true,true,true,false};
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
	private final static String [] virtualDevicesStrings = {"浊度补偿系数"};
	private final static String [] ranges={"2mg/L","5mg/L","10mg/L"};
	/**
	 * 使能虚拟设备命令
	 */
	private final static byte [] enableVirtualDevicesCmd ={0x01,0x11,0x04,0x00,0x03,0x00,0x0A,0x0d,0x0a};
	private final static byte [] vdNum = {4,6,10,11,12};//虚拟设备编号
	public TnEquipmentInfo() {
		// TODO 自动生成的构造函数存根

	}

	public EquipmentData getEquipmentData() {
		// TODO 自动生成的方法存根
		if (data==null) {
			data = new EquipmentData(pump, valve, vd, vdOn, vdOff,vdNum);
			data.setHasInjectionPump(false);
			data.setHasRobotArm(false);
		}
		return data;
	}

	@Override
	public String[] getVirtualDevicesString() {
		// TODO 自动生成的方法存根
		return virtualDevicesStrings;
	}	

	@Override
	public byte[] getEnableVirtualDevicesCmd() {
		// TODO 自动生成的方法存根
		return enableVirtualDevicesCmd;
	}

/*	@Override
	public byte[] getVirtualDevicesParams(int name, String params) {
		// TODO 自动生成的方法存根
		byte[] cmd ={0x01,0x14,0x12,0x01,0x01,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0d,0x0a};
		return cmd;
	}*/

	@Override
	public String getResult(float result) {
		// TODO 自动生成的方法存根
		DecimalFormat fnum = new DecimalFormat("##0.000"); //保留小数点后3位
		
		return fnum.format(result); 
		//Log.d(tag,"Result = "+  fnum.format(data));
		//return String.valueOf(result);
	}

	@Override
	public String[] getDevicesRangStrings() {
		// TODO 自动生成的方法存根
		return ranges;
	}

	@Override
	public String getVirtualDevices(int pos,CommandSerialPort com) {
		// TODO 自动生成的方法存根
		Log.d(tag, "读参数");
		if (pos==0) {	
			//return "2";
			return String.valueOf(myApplication.getInstance().getDualCompute().getTurbCompenation());
		}
		else {
			return "None";
		}
	}

	@Override
	public String getUnit() {
		return unit;
	}

    @Override
    public int getSampleNumber(int type) {
        return 1;
    }

    @Override
    public String getTag() {
        return null;
    }


    @Override
	public void setVirtualDevices(int pos, String params, CommandSerialPort com) {
		// TODO 自动生成的方法存根
		/*if (pos<2) {
			byte[] cmd ={0x01,0x14,0x12,0x01,0x01,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0f,0x0d,0x0a};
			com.Send(cmd);
		}
		else {
			myApplication.getInstance().getDualCompute().setTurbCompenation(Float.valueOf(params));
			myApplication.getInstance().putConfig("TurbCompensation", Float.valueOf(params));
		}*/
		
		if (pos == 0) {
			myApplication.getInstance().getDualCompute().setTurbCompenation(Float.valueOf(params));
			myApplication.getInstance().putConfig("TurbCompensation", Float.valueOf(params));
		}
	}

}
