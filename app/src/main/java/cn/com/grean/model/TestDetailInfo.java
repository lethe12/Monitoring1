package cn.com.grean.model;

import cn.com.grean.tools;

public class TestDetailInfo {
	private String lastTestInfo;
	private long nextTestDate;
	private long autoTestInterval;
	private boolean autoEnable;

	public boolean isAutoEnable() {
		return autoEnable;
	}

	public TestDetailInfo(String info,long date,long interval,boolean autoEnable) {
		// TODO 自动生成的构造函数存根
		this.lastTestInfo = "测量信息:"+info;
		this.nextTestDate = date;
		this.autoTestInterval = interval;
		this.autoEnable = autoEnable;
	}
	
	public String getLastTestInfo(){
		return lastTestInfo;
	}
	
	public String getNextTastDate(){
		return "下次测量时间:"+tools.timestamp2string(nextTestDate);
	}
	
	public String getAutoTestInterval(){
		return String.valueOf(autoTestInterval/60000l);
	}

}
