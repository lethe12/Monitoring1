package cn.com.grean.script.plan;

import cn.com.grean.myApplication;

public class AutoTestPlan extends PlanOfScript implements ScheduleOfScriptRun{


	public AutoTestPlan() {
		super();
		// TODO 自动生成的构造函数存根
	}

	@Override
	public long getNexDate(long last) {
		// TODO 自动生成的方法存根
		long interval = myApplication.getInstance().getConfigLong("AutoTestInterval");
		return super.getNext(last, interval);
	}

	@Override
	public void timing(ScheduleListener scheduleListener,long date) {
		// TODO 自动生成的方法存根
		if (scheduleListener != null) {
			myApplication.getInstance().putConfig("AutoTestDate", date);
			scheduleListener.notify(ScheduleListener.AUTOTest, true,date);
		}
	}
}
