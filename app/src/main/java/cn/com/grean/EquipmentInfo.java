package cn.com.grean;

import cn.com.grean.script.instruction.CommandSerialPort;

public interface EquipmentInfo {
	/**
	 * 获取设备的仪器信息
	 * @return
	 */
	EquipmentData getEquipmentData();
	/**
	 * 获取虚拟设备名称列表
	 * @return
	 */
	String [] getVirtualDevicesString();//获取虚拟设备列表
	/**
	 * 设置参数
	 * @param pos
	 * @param params
	 * @param com
	 */
	void setVirtualDevices(int pos,String params,CommandSerialPort com);
	/**
	 * 使能虚拟设备命令
	 * @return
	 */
	byte [] getEnableVirtualDevicesCmd();//获取使能虚拟设备命令
	/**
	 * 将测量结果转化为字符串显示
	 * @param result
	 * @return
	 */
	String getResult(float result);
	/**
	 * 获取量程字符串
	 * @return
	 */
	String [] getDevicesRangStrings();
	/**
	 * 获取设备参数
	 * @param pos
	 * @param com
	 * @return
	 */
	String getVirtualDevices(int pos,CommandSerialPort com);

	/**
	 * 获取单位
	 * @return
	 */
	String getUnit();

	int getSampleNumber(int type);

	String getTag();


}
