package cn.com.grean.script.algorithm;

import android.content.SharedPreferences;

public class ComputerDirector {
	private ComputerBuilder builder;

	public ComputerDirector(ComputerBuilder builder) {
		// TODO 自动生成的构造函数存根
		this.builder = builder;
	}
	
	public ComputerParams construct(SharedPreferences sp){
		
		builder.setCalibrationConsumerParams(sp.getFloat("ConsumerSlope", 1f), sp.getFloat("ConsumerIntercept", 0f));
		builder.setCalibrationLastParams(sp.getFloat("lastValueLow", 0.001f), sp.getFloat("lastValueHigh", 1f), sp.getFloat("lastTargetLow", 0f), sp.getFloat("lastTargetHigh", 0.5f));
		builder.setCalibrationLimiti(sp.getFloat("slopeMax", 100f), sp.getFloat("slopeMin", 0f), sp.getFloat("interceptMax", 100f), sp.getFloat("interceptMin", -100f));
		builder.setCalibrationParams(sp.getFloat("slope", 1f), sp.getFloat("intercept", 0f));
		builder.setCurrentResult(sp.getFloat("CurrentResult", 0.0001f));
		builder.setTurbCompensation(sp.getFloat("TurbCompensation", 2.0f));
		return builder.getComputerParams();
	}

}
