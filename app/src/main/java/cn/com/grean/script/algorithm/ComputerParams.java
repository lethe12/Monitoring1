package cn.com.grean.script.algorithm;

public class ComputerParams {
	private float resultMax = 999f;//上限
	private float resultMin = 0f;//下限
	private float result;//计算结果
	private float value;//测量结果
	private float slope;//斜率
	private float intercept;//截距
	private float slopeMax;//最大斜率
	private float slopeMin;//最小值
	private float interceptMax;//最大截距
	private float interceptMin;//最小截距
	private float lastValueLow;
	private float lastValueHigh;
	private float lastTargetLow;
	private float lastTargetHigh;
	private float consumerSlope;
	private float consumerIntercept;
	private float turbCompensation;//浊度补充
	
	public ComputerParams() {
		// TODO 自动生成的构造函数存根
	}

	public float getResultMax() {
		return resultMax;
	}

	public float getResultMin() {
		return resultMin;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getSlope() {
		return slope;
	}

	public void setSlope(float slope) {
		this.slope = slope;
	}

	public float getIntercept() {
		return intercept;
	}

	public void setIntercept(float intercept) {
		this.intercept = intercept;
	}

	public float getSlopeMax() {
		return slopeMax;
	}

	public void setSlopeMax(float slopeMax) {
		this.slopeMax = slopeMax;
	}

	public float getSlopeMin() {
		return slopeMin;
	}

	public void setSlopeMin(float slopeMin) {
		this.slopeMin = slopeMin;
	}

	public float getInterceptMax() {
		return interceptMax;
	}

	public void setInterceptMax(float interceptMax) {
		this.interceptMax = interceptMax;
	}

	public float getInterceptMin() {
		return interceptMin;
	}

	public void setInterceptMin(float interceptMin) {
		this.interceptMin = interceptMin;
	}

	public float getLastValueLow() {
		return lastValueLow;
	}

	public void setLastValueLow(float lastValueLow) {
		this.lastValueLow = lastValueLow;
	}

	public float getLastValueHigh() {
		return lastValueHigh;
	}

	public void setLastValueHigh(float lastValueHigh) {
		this.lastValueHigh = lastValueHigh;
	}

	public float getLastTargetLow() {
		return lastTargetLow;
	}

	public void setLastTargetLow(float lastTargetLow) {
		this.lastTargetLow = lastTargetLow;
	}

	public float getLastTargetHigh() {
		return lastTargetHigh;
	}

	public void setLastTargetHigh(float lastTargetHigh) {
		this.lastTargetHigh = lastTargetHigh;
	}

	public float getConsumerSlope() {
		return consumerSlope;
	}

	public void setConsumerSlope(float consumerSlope) {
		this.consumerSlope = consumerSlope;
	}

	public float getConsumerIntercept() {
		return consumerIntercept;
	}

	public void setConsumerIntercept(float consumerIntercept) {
		this.consumerIntercept = consumerIntercept;
	}

	public float getTurbCompensation() {
		return turbCompensation;
	}

	public void setTurbCompensation(float turbCompensation) {
		this.turbCompensation = turbCompensation;
	}

	
}
