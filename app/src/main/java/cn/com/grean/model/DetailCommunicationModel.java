package cn.com.grean.model;

import cn.com.grean.TCPListeners;

public interface DetailCommunicationModel {
	CommunicationInfo loadCommunicationInfo();//载入通讯模式
	String [] getSpinnerString();//获取spinner绑定的字符串
	String getBaudRateString(int index);//获取需要绑定的字符串
	void setSlaveBaudRate(int BaudRate);
	boolean setSlaveAddress(int address);
	void setCommunicationMode(boolean key);
	int getDefaultSlaveBaudRate();
	void setDefaultValue(float value);
	void rollBackDefaultValue();
	String getStartString();
	String getInquireString();
	String getRequireString();
	/**
	 * 判断是否联网
	 * @return true 已联网  false 断网
	 */
	boolean isConnected();
	/**
	 * 断开TCP链接
	 */
	void setTCPDisconnect();
	/**
	 * 建立TCP链接
	 * @param IP 目标IP
	 * @param port 目标端口
	 */
	void setTCPConnect(String IP,int port,TCPListeners listeners);
	/**
	 * 设置TCP链接的ID
	 * @param id
	 */
	void setTCPID(String id);
}
