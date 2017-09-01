package cn.com.grean.protocol;

public interface ProtocolProcessor {
	/**
	 * 字节流处理的方法
	 * @param rec 收到的字节流地址
	 * @param len 收到的字节流的长度
	 * @return 立即返回的数据 如=null则无返回
	 */
	byte[] BytesProtocol(byte []rec,int len);
	/**
	 * 字符流处理的方法
	 * @param rec 收到的字符流
	 * @return 立即返回的数据  如=null则无返回
	 */
	String ASCIIProtocol(String rec);
	/**
	 * 设置当前测量结果
	 * @param result
	 */
	void setProtocolResult(float result);
	/**
	 * 设置测量结果时间戳
	 * @param date
	 */
	void setProtocolTimeStamp(long date);
	/**
	 * 获取当前结果
	 * @return
	 */
	float getProtocolResult();
	/**
	 * 获取TCP心跳包字符流
	 * @return TCP心跳包字符流
	 */
	String getHeartString();
	/**
	 * 获取启动命令
	 * @return
	 */
	public byte [] getStartFrame();
	/**
	 * 获取结果命令
	 * @return
	 */
	public byte [] getInquireFrame();
	/**
	 * 预计数据查询命令
	 * @return
	 */
	public byte [] getReturnFrame();
	/**
	 * 设置字节流地址
	 * @param address
	 */
	public void setByteProtocolAddress(byte address);
	/**
	 * 设置ASCII协议的ID
	 * @param id
	 */
	public void setASCIIProtocolID(String id);
}
