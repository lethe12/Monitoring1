package cn.com.grean.script.algorithm;

import android.util.Log;

public class ComputeData {
	private final static String tag = "ComputeData";
	private float resultMax = 99f;//上限
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
	private ComputerListener listener;

	public ComputeData(ComputerParams params,ComputerListener listener) {
		// TODO 自动生成的构造函数存根
		this.slope = params.getSlope();
		this.slopeMax = params.getSlopeMax();
		this.slopeMin = params.getSlopeMin();
		this.intercept = params.getIntercept();
		this.interceptMax = params.getInterceptMax();
		this.interceptMin = params.getInterceptMin();
		this.lastTargetHigh = params.getLastTargetHigh();
		this.lastTargetLow = params.getLastTargetLow();
		this.lastValueHigh = params.getLastValueHigh();
		this.lastValueLow = params.getLastValueLow();
		this.resultMax = params.getResultMax();
		this.result = params.getResult();
		this.resultMin = params.getResultMin();
		this.consumerIntercept = params.getConsumerIntercept();
		this.consumerSlope = params.getConsumerSlope();
		this.listener = listener;
	}
		
	protected float getLastTargetLow() {
		return lastTargetLow;
	}


	protected void setLastTargetLow(float lastTargetLow) {
		this.lastTargetLow = lastTargetLow;
		listener.saveData("lastTargetLow", lastTargetLow);
	}



	protected float getLastTargetHigh() {
		return lastTargetHigh;
	}



	protected void setLastTargetHigh(float lastTargetHigh) {
		this.lastTargetHigh = lastTargetHigh;
		listener.saveData("lastTargetHigh", lastTargetHigh);
	}



	protected float getLastValueLow() {
		return lastValueLow;
	}

	protected float getLastValueHigh() {
		return lastValueHigh;
	}

	protected float getResult() {
		return result;
	}

	protected float getValue() {
		return value;
	}

	protected float getSlope() {
		return slope;
	}

	protected float getIntercept() {
		return intercept;
	}
	
	protected float computeResult(float value){
		result = value*slope+intercept;
		this.value = value;
		if (result < resultMin) {
			result = resultMin;
		}
		else if(result > resultMax){
			result = resultMax;
		}		
		return result;
	}
	//计算高点校准值
	protected boolean computeHighCalibration(float valueHigh) {
		lastValueHigh = valueHigh;
		listener.saveData("lastValueHigh", valueHigh);
		return computeCalibration(lastValueLow, valueHigh, lastTargetLow, lastTargetHigh);
	}
	
	protected boolean computeLowCalibration(float valueLow){
		lastValueLow = valueLow;
		listener.saveData("lastValueLow", valueLow);
		return computeCalibration(valueLow, lastValueHigh, lastTargetLow, lastTargetHigh);
	}
	
	private boolean computeCalibration(float valueLow,float valueHigh,float targetLow, float targetHigh){
		float s,i;
		s = (targetHigh - targetLow)/(valueHigh - valueLow);
		i = targetHigh - s*valueHigh;
		
		Log.d(tag, "s="+String.valueOf(s)+";i="+String.valueOf(i)+";vl="+String.valueOf(valueLow)+";vh="+String.valueOf(valueHigh)+
				";tl="+String.valueOf(targetLow)+";th="+String.valueOf(targetHigh));
		if ((s<slopeMin)||(s>slopeMax)) {
			return false;
		}
		
		if ((i<interceptMin)||(i>interceptMax)) {
			return false;
		}
		
		intercept = i;
		slope = s;	
		listener.saveData("intercept", i);
		listener.saveData("slope", s);
		return true;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return "ComputeData";
	}

	public float getConsumerIntercept() {
		return consumerIntercept;
	}

	public void setConsumerIntercept(float consumerIntercept) {
		this.consumerIntercept = consumerIntercept;
	}

	public float getConsumerSlope() {
		return consumerSlope;
	}

	public void setConsumerSlope(float consumerSlope) {
		this.consumerSlope = consumerSlope;
	}

}
