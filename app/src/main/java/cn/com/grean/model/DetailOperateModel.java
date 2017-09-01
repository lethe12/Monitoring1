package cn.com.grean.model;

public interface DetailOperateModel {	
	void testStop();
	boolean testStandard();
	boolean testOnce();
	void saveAutoTestSwitch(boolean key,NotifyAutoTestChanged notify);
	void saveAutoTestInterval(long interval,NotifyAutoTestChanged notify);
	void saveNextTestDate(long date,NotifyAutoTestChanged notify);
	void loadDetailMeasure(TestInfoListener reTestInfoListener);//下载测量界面信息
	interface TestInfoListener{
		void onComplete(TestDetailInfo testDetailInfo);
	}
	public interface NotifyAutoTestChanged{
		void onComplete(boolean enable,long date,long interval);
	}
}
