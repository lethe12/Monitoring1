package cn.com.grean.Presenter;

public interface CalibrationDetailPresenter {
	void ShowInfo();//显示静态信息
	boolean calHigh(float data);
	boolean calLow(float data);
	boolean oneKeyToCal(float low,float high);
	void SwitchAutoCalModel(boolean key);
	void SetNextCalDate(long date);
	void setAutoCalInterval(long interval);

}
