package cn.com.grean.script.algorithm;

/**
 * 所有指标计算方法的接口
 * @author Administrator
 *
 */
public interface Compute {
	/**
	 * 设置第一次测量值
	 * @param data
	 */
	public void setFirstValue(float data);
	/**
	 * 设置第二次测量值
	 * @param data
	 */
	public void setSecondValue(float data);
	/**
	 * 获取中间值
	 * @return
	 */
	public float getMediumValue();
	/**
	 * 用于显示最近一次测量的信息
	 * @return
	 */
	public String getTestInfo();
	/**
	 * 用于显示校准信息
	 * @return
	 */
	public String getCalibrationInfo();
	/**
	 * 计算结果方法
	 * @param data
	 * @return
	 */
	public float Result(float data);
	/**
	 * 高点校准方法
	 * @param data 当前值
	 * @param index 当前校准的指正
	 * @param times 总的校准平均次数
	 */
	public void calibrationHigh(float data,int index,int times);
	/**
	 * 低点校准方法
	 * @param data 当前信号
	 * @param index 当前校准指针
	 * @param times 总得校准平均次数
	 */
	public void calibrationLow(float data,int index,int times);
	/**
	 * 设置高点校准值
	 * @param data
	 */
	public void setHighPoint(float data);//
	/**
	 * 设置低点校准值
	 * @param data
	 */
	public void setLowPoint(float data);//
	/**
	 * 获取测量时记录日志的文本
	 * @return
	 */
	public String getTestLogInfo();//
	/**
	 * 获取高点校准的日志信息
	 * @return
	 */
	public String getCalibrationHighLogInfo();
	/**
	 * 获取低点教主的日志信息
	 * @return
	 */
	public String getCalibrationLowLogInfo();//
	/**
	 * 获取当前计算结果
	 * @return mg/L
	 */
	public float getComputeResult();
	/**
	 * 直接设置修正参数
	 * @param slope 斜率
	 * @param intercept 截距
	 */
	public void setCoefficient(float slope,float intercept);


}
