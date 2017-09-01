package cn.com.grean;

public interface TCPListeners {
	/**
	 * 监听连接结果
	 * @param connected true 已联网 ;false 连接服务器失败 
	 */
	void onComplete(boolean connected);
}
