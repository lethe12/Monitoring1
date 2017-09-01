package cn.com.grean.model;

import java.text.DecimalFormat;

import cn.com.grean.myApplication;
import cn.com.grean.tools;

/**
 * 定义一次测量所需要显示信息
 * 
 * 
 * @author Administrator
 *
 */
public class MeasureInfo {
	private String resultString;//测量结果的数据
	private float result;//测量结果
	private String dateString;
	private String autoTestDateString;
	private String autoCalDateString;
	

	public String getAutoCalDateString() {
		return autoCalDateString;
	}


	public String getDateString() {
		return dateString;
	}


	public String getResultString() {
		return resultString;
	}

	public String getDevicesName() {
		return myApplication.getInstance().getParameterName();
	}


	public MeasureInfo(float result,long date,boolean autoTestEnable,long autoTest,boolean autoCalEnable,long autoCal) {
		// TODO 自动生成的构造函数存根
		this.result = result;
		if (date == 0) {
			resultString = "Nan";
			dateString = "测量时间:";
		}
		else {			
			DecimalFormat fnum = new DecimalFormat("##0.000"); //保留小数点后3位
			resultString=fnum.format(result); 
			dateString = "测量时间:"+tools.timestamp2string(date);
		}
		if(autoCalEnable){			
			autoCalDateString = "下次校准时间:"+ tools.timestamp2string(autoCal);
		}
		else {
			autoCalDateString = "下次校准时间:";
		}
		
		if (autoTestEnable) {
			
			autoTestDateString = "下次测量时间:"+ tools.timestamp2string(autoTest);
		}
		else {
			autoTestDateString = "下次测量时间:";
		}
	}


	public float getResult() {
		return result;
	}


	public String getAutoTestDateString() {
		return autoTestDateString;
	}


	



}
