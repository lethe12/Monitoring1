package cn.com.grean.monitoring;

public class MeasureInfoFactory {
	private MeasureInfoData data;
	private String info="";
	private int count=0,max=100;
	
	
	public MeasureInfoFactory() {
		// TODO 自动生成的构造函数存根
	}


	public MeasureInfoData getData() {
		return data;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public void setMax(int max) {
		this.max = max;
	}
	
	public MeasureInfoData build(){
		data = new MeasureInfoData(info, count, max);
		return data;
	}
	

}
