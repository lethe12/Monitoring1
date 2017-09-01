package cn.com.grean.script.plan;

import cn.com.grean.myApplication;

public class AutoCalPlan extends PlanOfScript implements ScheduleOfScriptRun{

	public AutoCalPlan() {
		// TODO 自动生成的构造函数存根
		super();
	}

	@Override
	public long getNexDate(long last) {
		// TODO 自动生成的方法存根
		long interval = myApplication.getInstance().getConfigLong("AutoCalInterval");
		return super.getNext(last, interval);
	}

	@Override
	public void timing(ScheduleListener scheduleListener,long date) {
		// TODO 自动生成的方法存根
		if (scheduleListener != null) {
			myApplication.getInstance().putConfig("AutoCalDate", date);
			scheduleListener.notify(ScheduleListener.AUTOCalibration, true,date);
		}
	}

}
