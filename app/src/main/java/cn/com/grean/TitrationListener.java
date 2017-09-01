package cn.com.grean;

public interface TitrationListener {
	void startTitration();//开始滴定
	boolean isCompleteTitration();//是否滴定完成
	float getTitrationTime();//获取滴定时间
}
