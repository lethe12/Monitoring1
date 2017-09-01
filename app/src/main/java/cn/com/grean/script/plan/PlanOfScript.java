package cn.com.grean.script.plan;

import cn.com.grean.tools;
/**
 * 脚本计划类
 * @author Administrator
 *
 */
public class PlanOfScript {
	public PlanOfScript() {
		// TODO 自动生成的构造函数存根

	}


	public long getNext(long last,long interval) {
		long now = tools.nowtime2timestamp();
		return tools.calcNextTime(now, last, interval);
	}


}
