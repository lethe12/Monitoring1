package cn.com.grean.Presenter;

public interface MeasureDetailPresenter {
	void ShowInfo();//显示静态信息
	boolean TestOnce();
	boolean TestStandard();
	void StopTest();
	void SwitchAutoTestModel(boolean key);
	void SetNextTestDate(long date);
	void setAutoTestInterval(long interval);
}
