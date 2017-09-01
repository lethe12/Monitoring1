package cn.com.grean.monitoring;

public class MeasureInfoData {
	private String stateInfo="当前状态:";
	private int proCount,proMax;
	public MeasureInfoData(String info,int count,int max) {
		// TODO 自动生成的构造函数存根
		this.stateInfo = info;
		proCount = count;
		proMax = max;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public int getProCount() {
		return proCount;
	}
	public int getProMax() {
		return proMax;
	}

}
