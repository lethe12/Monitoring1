package cn.com.grean.Presenter;

public interface DetailSystemPresenter {
	String [] getBackLightTimeString();
	void showDitail();
	void saveSystemTime(int year,int month,int day,int hour,int min);
	void showBackLightTime(int pos);
}
