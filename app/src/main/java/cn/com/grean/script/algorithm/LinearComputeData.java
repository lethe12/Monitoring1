package cn.com.grean.script.algorithm;

public class LinearComputeData extends ComputeData implements Compute{

	private float result;
	float voltage;
	private boolean isCalibrationRigh;
	
	public LinearComputeData(ComputerParams params,ComputerListener listener) {
		// TODO 自动生成的构造函数存根
		super(params,listener);
		this.result = params.getResult();		
	}

	@Override
	public void setFirstValue(float data) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public String getTestInfo() {
		// TODO 自动生成的方法存根
		return "测量结果为:"+String.valueOf(result)+";电压为:"+String.valueOf(voltage)+"s";
	}

	@Override
	public String getCalibrationInfo() {
		// TODO 自动生成的方法存根
		return "当前使用斜率:"+String.valueOf(super.getSlope())+";截距:"+String.valueOf(super.getIntercept())+
				";\n最近一次高点校准高点电压:"+String.valueOf(super.getLastValueHigh())+";低点电压:"+String.valueOf(super.getLastValueLow());
	}

	@Override
	public float Result(float data) {
		// TODO 自动生成的方法存根
		voltage = data;
		result = super.computeResult(data);
		result = result*super.getConsumerSlope()+super.getConsumerIntercept();
		return result;
	}

	@Override
	public void calibrationHigh(float data,int index,int times) {
		// TODO 自动生成的方法存根
		voltage = data;
		isCalibrationRigh = super.computeHighCalibration(data);
	}

	@Override
	public void calibrationLow(float data,int index,int times) {
		// TODO 自动生成的方法存根
		voltage = data;
		isCalibrationRigh = super.computeLowCalibration(data);
	}

	@Override
	public void setHighPoint(float data) {
		// TODO 自动生成的方法存根
		super.setLastTargetHigh(data);
	}

	@Override
	public void setLowPoint(float data) {
		// TODO 自动生成的方法存根
		super.setLastTargetLow(data);
	}

	@Override
	public String getTestLogInfo() {
		// TODO 自动生成的方法存根
		return "测量结果:"+String.valueOf(result)+"电压:"+String.valueOf(voltage)+"s;";
	}

	@Override
	public String getCalibrationHighLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "高点校准成功,斜率:"+String.valueOf(super.getSlope())+";截距:"+String.valueOf(super.getIntercept())+";电压:"+String.valueOf(voltage)+
					"s;标液浓度:"+String.valueOf(super.getLastTargetHigh())+"mg/L;";
		}
		else {
			string = "高点校准失败,测量电压:"+String.valueOf(voltage)+"s;标液浓度:"+String.valueOf(super.getLastTargetHigh());
		}
		return string;
	}

	@Override
	public String getCalibrationLowLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "低点校准成功,斜率:"+String.valueOf(super.getSlope())+";截距:"+String.valueOf(super.getIntercept())+";电压:"+String.valueOf(voltage)+
					"s;标液浓度:"+String.valueOf(super.getLastTargetLow())+"mg/L;";
		}
		else {
			string = "低点校准失败,电压:"+String.valueOf(voltage)+";标液浓度:"+String.valueOf(super.getLastTargetLow())+
					"mg/L;";
		}
		return string;
	}

	@Override
	public float getComputeResult() {
		// TODO 自动生成的方法存根
		return result;
	}

	@Override
	public void setCoefficient(float slope, float intercept) {
		// TODO 自动生成的方法存根
		super.setConsumerIntercept(intercept);
		super.setConsumerSlope(slope);
	}

	@Override
	public void setSecondValue(float data) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public float getMediumValue() {
		// TODO 自动生成的方法存根
		return 0;
	}

}
