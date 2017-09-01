package cn.com.grean.monitoring;

public interface WarningDetailFragmentView {
	void showWarningInfo(String info);
	void showFloor(String floor);
	void showUpper(String upper);
	void clearInfo();
}
