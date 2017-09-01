package cn.com.grean.model;

public interface DetailSystemModel {
	int getBackLightTime();
	void loadBackLightTime();
	void setSystem(int year,int month,int day,int hour,int min);
	String [] loadBackLightTimeString();
	void setBackLightTime(int pos);
	String getNowDateString();
}
