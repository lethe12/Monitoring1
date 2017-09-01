package cn.com.grean;

public interface TCPController {
	/**
	 * 获取是否联网状态
	 * @return true 已联网;false 已断网 
	 */
	boolean isConnected();
	/**
	* 建立新的TCP链接
	 * @param IP 目标IP
	 * @param port 目标端口
	 * @param listeners 监听TCP连接情况
	 */
	/**
	 * 建立新的TCP链接
	 * @param IP 目标IP
	 * @param port 目标端口
	 * @param listeners 监听TCP连接情况
	 * @return true 开始链接 ;false 已断开链接
	 */
	boolean setTCPConnect(String IP,int port,TCPListeners listeners);
	/**
	 * 断开TCP链接
	 */
	void setTCPDisconnect();
}
