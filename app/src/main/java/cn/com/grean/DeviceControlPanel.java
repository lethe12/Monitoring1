package cn.com.grean;
/**
 * 控制仪器 测量、停止、校准等动作
 * @author Administrator
 *
 */
public interface DeviceControlPanel {
	/**
	 * 获取仪器当前是否正在运行
	 * @return 
	 * true 正忙
	 * false 空闲状态
	 */
	boolean isBusy();
	/**
	 * 启动测量
	 */
	void setDeviceTest();
	/**
	 * 启动校准
	 */
	void setDeviceCalibration();
	/**
	 * 停止当前操作
	 */
	void setDeviceStop();
	/**
	 * 测量标液
	 */
	void setDeviceTestStandard();
	/**
	 * 初始化
	 */
	void setDeviceInit();
}
