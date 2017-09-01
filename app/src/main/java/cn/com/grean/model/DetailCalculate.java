package cn.com.grean.model;

import cn.com.grean.myApplication;

public class DetailCalculate implements DetailCalculateModel{
	float slope,intercept,result;	
	int lowCalTimes,highCalTimes;

	public DetailCalculate() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public float getConsumerSlope() {
		// TODO 自动生成的方法存根
		return slope;
	}

	@Override
	public float getConsumerIntercept() {
		// TODO 自动生成的方法存根
		return intercept;
	}

	@Override
	public boolean setConsumerCoefficient(float slope, float intercept) {
		// TODO 自动生成的方法存根
		if (slope == 0f) {
			return false;
		}
		else {		
			this.slope = slope;
			this.intercept = intercept;
			myApplication.getInstance().putConfig("ConsumerSlope", slope);
			myApplication.getInstance().putConfig("ConsumerIntercept", intercept);
			myApplication.getInstance().getCompute().setCoefficient(slope, intercept);
			return true;
		}
	}

	@Override
	public float getNowValue() {
		// TODO 自动生成的方法存根
		return result*slope+intercept;
	}

	@Override
	public void loadCoefficient() {
		// TODO 自动生成的方法存根
		slope = myApplication.getInstance().getConfigFloat("ConsumerSlope");
		intercept = myApplication.getInstance().getConfigFloat("ConsumerIntercept");
		lowCalTimes = myApplication.getInstance().getConfigInt("LowCalibrationTimes");
		highCalTimes = myApplication.getInstance().getConfigInt("HighCalibrationTimes");		
		float temp = myApplication.getInstance().getConfigFloat("CurrentResult");
		if (slope == 0f) {
			slope = 0.0001f;
		}
		result = (temp - intercept)/slope;
	}

	@Override
	public int getLowCalTimes() {
		// TODO 自动生成的方法存根
		return lowCalTimes;
	}

	@Override
	public int getHighCalTimes() {
		// TODO 自动生成的方法存根
		return highCalTimes;
	}

	@Override
	public boolean saveCalTimes(int lowTimes, int highTimes) {
		// TODO 自动生成的方法存根
		if ((lowTimes==0)||(highTimes==0)) {
			
			return false;
		}
		else {
			lowCalTimes = lowTimes;
			highCalTimes = highTimes;
			myApplication.getInstance().putConfig("LowCalibrationTimes", lowCalTimes);
			myApplication.getInstance().putConfig("HighCalibrationTimes", highCalTimes);
			return true;
		}
	}

	

}
