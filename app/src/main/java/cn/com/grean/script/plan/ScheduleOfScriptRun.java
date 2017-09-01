package cn.com.grean.script.plan;

public interface ScheduleOfScriptRun {
	long getNexDate(long last);
	void timing(ScheduleListener scheduleListener,long date);
}
