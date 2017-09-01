package cn.com.grean.script;

import cn.com.grean.tools;

/**
 * 记录日志的格式
 * @author Administrator
 *
 */
public class LogFormat {
	private long timstamp;
	private String text;
	public LogFormat(String info) {
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
		return "LogFormat";
	}

}
