package cn.com.grean.script.algorithm;

public abstract class ComputerBuilder {
	/**
	 * 设置校准信息
	 * @param slope 斜率
	 * @param intercept 截距
 	 */
	public abstract void setCalibrationParams(float slope,float intercept);
	/**
	 * 校准参数的极限值
	 * @param slopeMax 最大斜率
	 * @param slopeMin 最小斜率
	 * @param interceptMax 最大截距
	 * @param interceptMin 最小截距
	 */
	public abstract void setCalibrationLimiti(float slopeMax,float slopeMin,float interceptMax,float interceptMin);
	/**
	 * 最近有一次校准参数
	 * @param lastValueLow 最近一次低点校准测量值
	 * @param lastValueHigh 最近一次高点校准测量值
	 * @param lastTargetLow 最近一次低点校准标液液值
	 * @param lastTargetHigh 最近一次高点标定标液值
	 */
	public abstract void setCalibrationLastParams(float lastValueLow,float lastValueHigh,float lastTargetLow,float lastTargetHigh);
	/**
	 * 修正值
	 * @param consumerSlope 斜率
	 * @param consumerIntercept 截距
	 */
	public abstract void setCalibrationConsumerParams(float consumerSlope,float consumerIntercept);
	/**
	 * 设置最近一次的测量值
	 * @param currentResult
	 */
	public abstract void setCurrentResult(float currentResult);
	/**
	 * 浊度补偿系数
	 * @param turbCompensation
	 */
	public abstract void setTurbCompensation(float turbCompensation);
	/**
	 * 获取实例
	 * @return
	 */
	public abstract ComputerParams getComputerParams();
}
