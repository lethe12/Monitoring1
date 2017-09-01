package cn.com.grean.script.plan;

public class NoneTestPlan implements ScheduleOfScriptRun{

	public NoneTestPlan() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public long getNexDate(long last) {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public void timing(ScheduleListener scheduleListener,long date) {
		// TODO 自动生成的方法存根
		/*if (scheduleListener!= null) {
			scheduleListener.notify(ScheduleListener.AUTOTest, false);
		}*/
	}

}
