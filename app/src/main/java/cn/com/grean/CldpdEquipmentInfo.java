package cn.com.grean;

import java.text.DecimalFormat;

import cn.com.grean.script.instruction.CommandSerialPort;

public class CldpdEquipmentInfo implements EquipmentInfo{
	private final static String tag = "CldpdEquipmentInfo";
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
	private final static String [] virtualDevicesStrings = {"光源","加热器"};
	private final static String [] ranges={"0~2mg/L"};
	/**
	 * 使能虚拟设备命令
	 */
	private final static byte [] enableVirtualDevicesCmd ={0x01,0x11,0x04,0x00,0x03,0x00,0x0A,0x0d,0x0a};
	private final static byte [] vdNum = {4,6,10,11,12};//虚拟设备编号
	
	public CldpdEquipmentInfo() {
		// TODO 自动生成的构造函数存根
	}

	public EquipmentData getEquipmentData() {
		// TODO 自动生成的方法存根
		if (data==null) {
			data = new EquipmentData(pump, valve, vd, vdOn, vdOff,vdNum);
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

	@Override
	public String getResult(float result) {
		// TODO 自动生成的方法存根
		DecimalFormat fnum = new DecimalFormat("##0.000"); //保留小数点后3位		
		return fnum.format(result); 
	}

	@Override
	public String[] getDevicesRangStrings() {
		// TODO 自动生成的方法存根
		return ranges;
	}

	
	@Override
	public void setVirtualDevices(int pos, String params, CommandSerialPort com) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public String getVirtualDevices(int pos, CommandSerialPort com) {
		// TODO 自动生成的方法存根
		return "None";
	}
}
