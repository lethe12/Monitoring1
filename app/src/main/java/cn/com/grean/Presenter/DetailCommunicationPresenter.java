package cn.com.grean.Presenter;

public interface DetailCommunicationPresenter {
	void showDetail();
	String [] getSlaveBaudRate();
	void setSlaveBaudRate(int position);
	boolean setSlveAddress(int address);
	void setCommunicationMode(boolean key);
	int getDefaultSlaveBaudRate();
	void setDefaultValue(float value);
	void returnDefault();
	/**
	 * 联网
	 * @param IP 服务器IP
	 * @param port 服务器端口
	 * @param id 本机ID
	 * @param enable 使能
	 */
	void setTCPConnect(String IP,int port,String id,boolean enable);
	/**
	 * 是否联网
	 * @return true 已连接  false 以断开连接
	 */
	boolean isTCPConnected();
}
