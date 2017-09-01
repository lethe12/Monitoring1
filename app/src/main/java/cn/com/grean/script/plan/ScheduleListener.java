package cn.com.grean.script.plan;

public interface ScheduleListener {
	public final static int NoChange = 0;
	public final static int AUTOTest = 1;
	public final static int AUTOCalibration = 2;
	public final static int CancelAll = 3;
	void notify(int kind,boolean key,long date);//脚本执行链结束时，执行该方法
	void isBusy(int kind);//执行时如遇正忙，则执行该方法
}
