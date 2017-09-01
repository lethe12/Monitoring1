package cn.com.grean.script.algorithm;

import cn.com.grean.tools;

/**
 * 检测双波长时使用的算法
 * TN Abs =  Abs220 -2* Abs75
 * @author Administrator
 *
 */
public class DualAbsCompute extends ComputeData implements DualWaveCompute,Compute{
	private final static String tag = "DualAbsCompute";
	private float firstIntensity,firstRefIntensity;
	private float secondIntensity,secondRefIntensity;
	private float absorbance;
	private float absorbanceSum=0;
	private float absorbanceAverage=0;
	private float result;
	private boolean isCalibrationRigh;
	private float turbCompensation;
	
	public DualAbsCompute(ComputerParams params,ComputerListener listener) {
		// TODO 自动生成的构造函数存根
		super(params,listener);
		this.result = params.getResult();
		this.turbCompensation = params.getTurbCompensation();
	}

	@Override
	public void setFirstValue(float data) {
		// TODO 自动生成的方法存根
		
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

	@Override
	public String getTestInfo() {
		// TODO 自动生成的方法存根
		return "结果:"+tools.float2String3(result)+"mg/L 吸光度:"+tools.float2String4(absorbance)+";";
		//+"参比光强:"+String.valueOf(firstIntensity)+";测量光强:"+String.valueOf(secondIntensity)+";";
	}

	@Override
	public String getCalibrationInfo() {
		// TODO 自动生成的方法存根
		return "当前使用斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+
				";\n最近一次高点校准高点吸光度:"+tools.float2String4(super.getLastValueHigh())+";低点吸光度:"+tools.float2String4(super.getLastValueLow());
	}

	@Override
	public float Result(float data) {
		// TODO 自动生成的方法存根
		return result;
	}

	@Override
	public void calibrationHigh(float data, int index, int times) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void calibrationLow(float data, int index, int times) {
		// TODO 自动生成的方法存根
		
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
		return "测量结果:"+tools.float2String3(result)+"mg/L 吸光度:"+tools.float2String4(absorbance)+";零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"
					+tools.float2String0(secondIntensity)+";参比零点光强:"+tools.float2String0(firstRefIntensity)+";参比测量光强:"
					+tools.float2String0(secondRefIntensity);
	}

	@Override
	public String getCalibrationHighLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "高点校准成功,斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+";吸光度:"+tools.float2String4(absorbance)+
					";标液浓度:"+tools.float2String3(super.getLastTargetHigh())+"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"
					+tools.float2String0(secondIntensity)+";参比零点光强:"+tools.float2String0(firstRefIntensity)+";参比测量光强:"
					+tools.float2String0(secondRefIntensity);
		}
		else {
			string = "高点校准失败,吸光度:"+tools.float2String4(absorbance)+";标液浓度:"+tools.float2String3(super.getLastTargetHigh())+
					"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity)+";参比零点光强:"
					+tools.float2String0(firstRefIntensity)+";参比测量光强:"+tools.float2String0(secondRefIntensity);
		}
		return string;
	}

	@Override
	public String getCalibrationLowLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "低点校准成功,斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+";吸光度:"+tools.float2String4(absorbance)+
					";标液浓度:"+tools.float2String3(super.getLastTargetLow())+"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"
					+tools.float2String0(secondIntensity)+";参比零点光强:"+tools.float2String0(firstRefIntensity)+";参比测量光强:"
					+tools.float2String0(secondRefIntensity);
		}
		else {
			string = "低点校准失败,吸光度:"+tools.float2String4(absorbance)+";标液浓度:"+tools.float2String3(super.getLastTargetLow())+
					"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity)+";参比零点光强:"
					+tools.float2String0(firstRefIntensity)+";参比测量光强:"+tools.float2String0(secondRefIntensity);
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
	public void setFirstValue(float data, float ref) {
		// TODO 自动生成的方法存根
		this.firstIntensity = data;
		this.firstRefIntensity = ref;
	}

	@Override
	public float Result(float data, float ref) {
		// TODO 自动生成的方法存根
		float value = (float) Math.log10(firstIntensity / data);
		float valueRef = (float)Math.log10(firstRefIntensity/ref);
		if (valueRef<0) {
			valueRef = 0;
		}
		this.secondIntensity = data;
		this.secondRefIntensity = ref;
		absorbance=value-turbCompensation*valueRef;
		result = super.computeResult(absorbance);
		result = result*super.getConsumerSlope()+super.getConsumerIntercept();
		return result;
	}

	@Override
	public void calibrationHigh(float data, float ref, int index, int times) {
		// TODO 自动生成的方法存根
		float value = (float) Math.log10(firstIntensity / data);
		float valueRef = (float)Math.log10(firstRefIntensity/ref);
		if (valueRef<0) {
			valueRef = 0;
		}
		
		this.secondIntensity = data;
		this.secondRefIntensity = ref;
		absorbance=value-2*valueRef;
		if (index == 1) {
			absorbanceSum = value-turbCompensation*valueRef;
			absorbanceAverage = absorbanceSum;
		}
		else if (index == times) {
			absorbanceSum += value-turbCompensation*valueRef;
			absorbanceAverage = absorbanceSum / times;			
		}
		else {
			absorbanceSum += absorbance;
			absorbanceAverage = absorbanceAverage / index;
		}
		
		if (index >= times) {
			isCalibrationRigh = super.computeHighCalibration(absorbanceAverage);
		}
	}

	@Override
	public void calibrationLow(float data, float ref, int index, int times) {
		// TODO 自动生成的方法存根
		float value = (float) Math.log10(firstIntensity / data);
		float valueRef = (float)Math.log10(firstRefIntensity/ref);
		if (valueRef<0) {
			valueRef = 0;
		}
		this.secondIntensity = data;
		this.secondRefIntensity = ref;
		
		absorbance=value-2*valueRef;;
		if (index == 1) {
			absorbanceSum = value-turbCompensation*valueRef;
			absorbanceAverage = absorbance;
		}
		else if (index == times) {
			absorbanceSum += value-turbCompensation*valueRef;;
			absorbanceAverage = absorbanceSum / times;
		}
		else {
			absorbanceSum += absorbance;
			absorbanceAverage = absorbanceAverage / index;
		}
		
		if (index >= times) {
			isCalibrationRigh = super.computeLowCalibration(absorbanceAverage);
		}
	}


	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return tag;
	}

	@Override
	public float getTurbCompenation() {
		// TODO 自动生成的方法存根
		return turbCompensation;
	}

	@Override
	public void setTurbCompenation(float data) {
		// TODO 自动生成的方法存根
		turbCompensation = data;
	}
	
	
}
