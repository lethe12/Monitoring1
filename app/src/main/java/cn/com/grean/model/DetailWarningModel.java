package cn.com.grean.model;

public interface DetailWarningModel {
	void clearWarning();
	void saveWarningInfo(float floor,float upper);
	String loadWarningInfo();
	float getWarningFloor();
	float getWarningUpper();	
}
