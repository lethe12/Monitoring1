package cn.com.grean.model;

import cn.com.grean.tools;

public class CalDetailInfo {
	private String lastTestInfo;
	private long nextTestDate;
	private long autoTestInterval;
	private boolean autoEnable;
	private float lowTarget;
	private float highTarget;

	public boolean isAutoEnable() {
		return autoEnable;
	}

	public CalDetailInfo(String info,long date,long interval,boolean autoEnable,float low,float high) {
		// TODO 自动生成的构造函数存根
		this.lastTestInfo = "校准信息:"+info;
		this.nextTestDate = date;
		this.autoTestInterval = interval;
		this.autoEnable = autoEnable;
		this.lowTarget = low;
		this.highTarget = high;
	}
	
	public String getLastCalInfo(){
		return lastTestInfo;
	}
	
	public String getNextCalDate(){
		return "下次校准时间:"+tools.timestamp2string(nextTestDate);
	}
	
	public String getAutoCalInterval(){
		return String.valueOf(autoTestInterval/60000l);
	}
	
	public String getLowTarget(){
		
		return tools.float2String3(lowTarget);
	}
	
	public String getHighTarget(){
		
		return tools.float2String3(highTarget);
	}
}
