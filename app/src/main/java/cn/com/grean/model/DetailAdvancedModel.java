package cn.com.grean.model;

public interface DetailAdvancedModel {
	String [] getScriptNameStrings();//获取所有脚本名称
	boolean loadScript(int name,int range);//从TF卡获得脚本
	String [] getScriptContentStrings(int name);//获取脚本内容
	String [] getDevicesNameStrings();//获取所有仪器名称
	int getNowDevicesName();//当前仪器的参数
	void setNowDevices(int pos);//设置当前仪器参数
	String [] getCommunicationProtocolStrings();//获取所有协议的名称
	int loadCommunicationProtocol();//装载通讯协议
	void setCommunicationProtocol(int name);//设置通讯协议
	String [] getVirtualDeviceStrings();//获取当前
	void setVirtualDevice(int name,String params);//设置虚拟设备参数
	void enableVirtualDevice();//使能虚拟设备
	float [] getCalibrationParams();//获取校准上下限
	boolean setCalibrationParams(float [] params);//设置校准参数
	void changMangerPassword(String password);//修改管理员密码
	/**
	 * 保存升级程序的URI
	 * @param uri
	 * @return
	 */
	boolean setUpdataUri(String uri);
	/**
	 * 获取升级的URI
	 * @return
	 */
	String getUpdataUri();
	/**
	 * 根据指标获取量程字符串
	 * @return
	 */
	String [] getRangesStrings();
	/**
	 * 获取当前指标的量程
	 * @return
	 */
	int getDevicesRange();
	/**
	 * 设置量程
	 * @param range
	 */
	void setDeviesRange(int range);
	/**
	 * 获取虚拟设备参数
	 * @param pos
	 * @return
	 */
	String getVirtualDevices(int pos);
}
