package cn.com.grean.model;

public interface DetailCalModel {
	boolean calHigh(float data);
	boolean calLow(float data);
	boolean oneKeyToCal(float low,float high);
	void saveAutoCalSwitch(boolean key,NotifyAutoCalChanged notify);
	void saveAutoCalDate(long date,NotifyAutoCalChanged notify);
	void saveAutoCalInterval(long interval,NotifyAutoCalChanged notify);
	void loadDetailCal(CalInfoListener listener);
	interface CalInfoListener{
		void onComplete(CalDetailInfo info);
	}
	public interface NotifyAutoCalChanged{
		void onComplete(boolean enable,long date,long interval);
	}
}
