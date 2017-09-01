package cn.com.grean.script.algorithm;

import cn.com.grean.tools;
import android.util.Log;

public class AbsorbanceComputeData extends ComputeData implements Compute{
	private final static String tag = "AbsorbanceComputeData";
	private float firstIntensity;
	private float secondIntensity;
	private float absorbance;
	private float absorbanceSum=0;
	private float absorbanceAverage=0;
	private float result;
	private boolean isCalibrationRigh;
	/**
	 * 初始化参数
	 * @param slope 计算斜率
	 * @param intercept 计算截距
	 * @param slopeMax 最大斜率
	 * @param slopeMin 最小斜率
	 * @param interceptMax 最大截距
	 * @param interceptMin 最小截距
	 * @param lastValueLow 最近一次低点校准的吸光度
	 * @param lastValueHigh 最经一次高点校准的吸光度
	 * @param lastTargetLow 低点浓度
	 * @param lastTargetHigh 高点浓度
	 * @param consumerSlope 修正斜率
	 * @param consumerIntercept 修正截距
	 * @param currentResult 最近一次测量结果
	 */
	public AbsorbanceComputeData(ComputerParams params,ComputerListener listener) {
		// TODO 自动生成的构造函数存根
		super(params,listener);
		this.result = params.getResult();
		/*Log.d(tag, String.valueOf(consumerIntercept));
		Log.d(tag, String.valueOf(consumerSlope));*/
	}
	
	public float Result(float secondIntensity) {
		float value = (float) Math.log10(firstIntensity / secondIntensity);
		this.secondIntensity = secondIntensity;
		absorbance=value;
		result = super.computeResult(value);
		result = result*super.getConsumerSlope()+super.getConsumerIntercept();
		Log.d(tag, "Result="+String.valueOf(result));
		return result;
	}
	

	@Override
	public void setFirstValue(float data) {
		// TODO 自动生成的方法存根
		firstIntensity = data;
	}

	@Override
	public String getTestInfo() {
		// TODO 自动生成的方法存根
		return "结果:"+tools.float2String3(result)+"mg/L;吸光度:"+tools.float2String4(absorbance)+";\n"
				+"零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity)+";";
	}

	@Override
	public String getCalibrationInfo() {
		// TODO 自动生成的方法存根
		return "当前使用斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+
				";\n最近一次高点校准高点吸光度:"+tools.float2String4(super.getLastValueHigh())+";低点吸光度:"+tools.float2String4(super.getLastValueLow());
	}

	@Override
	public void calibrationHigh(float data,int index,int times) {
		// TODO 自动生成的方法存根
		secondIntensity = data;
		float value = (float) Math.log10(firstIntensity / data);
		absorbance=value;
		if (index == 1) {
			absorbanceSum = value;
			absorbanceAverage = value;			
		}
		else if (index >= times) {
			absorbanceSum += value;
			absorbanceAverage = absorbanceSum / times;
		}
		else {
			absorbanceSum += value;
			absorbanceAverage = absorbanceSum / index;
		}
		
		if (index >= times) {
			isCalibrationRigh = super.computeHighCalibration(absorbanceAverage);			
		}
	}

	@Override
	public void calibrationLow(float data,int index,int times) {
		// TODO 自动生成的方法存根
		secondIntensity = data;
		float value = (float) Math.log10(firstIntensity / data);
		absorbance=value;
		if (index == 1) {
			absorbanceSum = value;
			absorbanceAverage = value;
		}
		else if (index >= times) {
			absorbanceSum += value;
			absorbanceAverage = absorbanceSum / times;
		}
		else {
			absorbanceSum += value;
			absorbanceAverage = absorbanceSum / index;
		}
		
		if (index >= times) {
			isCalibrationRigh = super.computeLowCalibration(absorbanceAverage);			
		}
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
		
		return "测量结果:"+tools.float2String3(result)+"mg/L;吸光度:"+tools.float2String4(absorbance)+";\n"
				+"零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity)+";";
	}

	@Override
	public String getCalibrationHighLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "高点校准成功,斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+";吸光度:"+tools.float2String4(absorbance)+
					";标液浓度:"+tools.float2String3(super.getLastTargetHigh())+"mg/L;零点光强:"+tools.float2String0(firstIntensity)+
					";测量光强:"+tools.float2String0(secondIntensity);
		}
		else {
			string = "高点校准失败,吸光度:"+tools.float2String3(absorbance)+";标液浓度:"+String.valueOf(super.getLastTargetHigh())+
					"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity);
		}
		return string;
	}

	@Override
	public String getCalibrationLowLogInfo() {
		// TODO 自动生成的方法存根
		String string;
		if (isCalibrationRigh) {
			string = "低点校准成功,斜率:"+tools.float2String4(super.getSlope())+";截距:"+tools.float2String4(super.getIntercept())+";吸光度:"+tools.float2String4(absorbance)+
					";标液浓度:"+tools.float2String3(super.getLastTargetLow())+"mg/L;零点光强:"+tools.float2String0(firstIntensity)+
					";测量光强:"+tools.float2String0(secondIntensity);
		}
		else {
			string = "低点校准失败,吸光度:"+tools.float2String4(absorbance)+";标液浓度:"+tools.float2String3(super.getLastTargetLow())+
					"mg/L;零点光强:"+tools.float2String0(firstIntensity)+";测量光强:"+tools.float2String0(secondIntensity);
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
