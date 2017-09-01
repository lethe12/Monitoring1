package cn.com.grean.script;

import cn.com.grean.tools;

public class ErrorFormat {
	
	private long timstamp;
	private String text;
	public ErrorFormat(String info) {
		// TODO 自动生成的构造函数存根
		timstamp = tools.nowtime2timestamp();
		text = info;
	}
	public String getText(){
		return text;
	}
	public long getDate(){
		return timstamp;
	}
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return "ErrorFormat";
	}	


}
