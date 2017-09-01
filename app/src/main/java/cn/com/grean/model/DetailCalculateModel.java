package cn.com.grean.model;

public interface DetailCalculateModel {
	float getConsumerSlope();//获取修正斜率
	float getConsumerIntercept();//获取修正截距
	boolean setConsumerCoefficient(float slope,float intercept);//保存系数
	float getNowValue();//计算现有值
	void loadCoefficient();
	int getLowCalTimes();
	int getHighCalTimes();
	boolean saveCalTimes(int lowTimes,int highTimes);
}
