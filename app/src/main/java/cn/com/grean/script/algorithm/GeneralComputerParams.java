package cn.com.grean.script.algorithm;

public class GeneralComputerParams extends ComputerBuilder{
	private ComputerParams params = new ComputerParams();
	public GeneralComputerParams() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void setCalibrationParams(float slope, float intercept) {
		// TODO 自动生成的方法存根
		params.setSlope(slope);
		params.setIntercept(intercept);
	}

	@Override
	public void setCalibrationLimiti(float slopeMax, float slopeMin, float interceptMax, float interceptMin) {
		// TODO 自动生成的方法存根
		params.setSlopeMax(slopeMax);
		params.setSlopeMin(slopeMin);
		params.setInterceptMax(interceptMax);
		params.setInterceptMin(interceptMin);
	}

	@Override
	public void setCalibrationLastParams(float lastValueLow, float lastValueHigh, float lastTargetLow, float lastTargetHigh) {
		// TODO 自动生成的方法存根
		params.setLastTargetLow(lastTargetLow);
		params.setLastTargetHigh(lastTargetHigh);
		params.setLastValueHigh(lastValueHigh);
		params.setLastValueLow(lastValueLow);
	}

	@Override
	public void setCalibrationConsumerParams(float consumerSlope, float consumerIntercept) {
		// TODO 自动生成的方法存根
		params.setConsumerIntercept(consumerIntercept);
		params.setConsumerSlope(consumerSlope);
	}

	@Override
	public void setCurrentResult(float currentResult) {
		// TODO 自动生成的方法存根
		params.setResult(currentResult);
	}

	@Override
	public ComputerParams getComputerParams() {
		// TODO 自动生成的方法存根
		return params;
	}

	@Override
	public void setTurbCompensation(float turbCompensation) {
		// TODO 自动生成的方法存根
		params.setTurbCompensation(turbCompensation);
	}

}
