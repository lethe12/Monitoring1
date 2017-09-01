package cn.com.grean.protocol;

import cn.com.grean.protocol.GeneralBytesProtocol.ControlProtocol;

public interface GeneralASCIIProtocol {
	/**
	 * 处理TCP字节流数据
	 * @param rec 收到的数据
	 * @return 立即返回的数据
	 */
	public String ProcessProtocol(String rec,ControlProtocol controlProtocol,ProtocolHistoryData data);
	/**
	 * 设置当前参数结果
	 * @param result
	 */
	void setResult(float result);
	/**
	 * 设置当前测量时间戳
	 * @param date
	 */
	void setTimeStamp(long date);
	/**
	 * 获取心跳包
	 * @return
	 */
	String getHeartString();
	/**
	 * 设置ASCII协议ID
	 * @param id
	 */
	void setID(String id);
}
