package cn.com.grean.protocol;

/**
 * 一般字节流控制接口
 * @author Administrator
 *
 */
public interface GeneralBytesProtocol {
	
	/**
	 * 处理字节流协议
	 * @param data 字节流数组
	 * @param len 字节流长度
	 * @return 所需返回的指令 如=null则无返回
	 */
	public byte [] ProcessProtocol (byte [] data,int len,ControlProtocol controlProtocol);
	/**
	 * 设置Modbus地址
	 * @param address 本机地址
	 */
	public void setModBusAddress(byte address);
	/**
	 * 控制仪器接口
	 * @author Administrator
	 *
	 */
	interface ControlProtocol{
		/**
		 * 启动测量
		 */
		public final static int TEST = 1;
		/**
		 * 启动校准
		 */
		public final static int CALIBRATION = 2;
		/**
		 * 启动测量质控样
		 */
		public final static int QUALITY_CONTROL = 3;
		/**
		 * 停止仪器
		 */
		public final static int STOP = 4;
		/**
		 * 初始化，自检
		 */
		public final static int INIT = 5;
		/**
		 * 查询系统状态 return true 为正忙 false 为空闲
		 */
		public final static int STATE = 6;
		/**
		 * 完成操作
		 * @param cmd
		 * @return =true 操作成功 ；=false 操作失败
		 */
		boolean onComplete(int cmd);
	}
	/**
	 * 设置当前显示结果
	 * @param result
	 */
	void setNowResult(float result);
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
	 * 获取当前检测结果
	 * @return
	 */
	float getNowResult();
}
