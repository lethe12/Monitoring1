package cn.com.grean;

public interface RS485MsgListener {
	/**
	 * 接收并显示收到的字节
	 * @param text
	 */
	void onReceiveOneMsg(String text);
}
