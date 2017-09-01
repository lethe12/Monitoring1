package cn.com.grean.monitoring;

public interface CalculateDetailFragmentView {
	void showInfo(String slope,String intercept,String result,String lowCalTimes,String highCalTimes);
	void updataNowResult(String text);
}
