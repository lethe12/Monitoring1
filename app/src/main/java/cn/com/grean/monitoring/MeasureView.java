package cn.com.grean.monitoring;

import cn.com.grean.model.MeasureInfo;

public interface MeasureView {
	/**
	 * 显示当前测试结果 时间 和指标名称
	 * @param measureInfo
	 */
	void showMeasureInfo(MeasureInfo measureInfo);
	void showCommandinfo(String info,int Progress,int Max);
	void showResultInfo(String result,String time);
	void showErrorICON(boolean key);//故障标志
	void showMaintainICON(boolean key);//维护标志
	void showOnlineICON(boolean key);//是否在线标志
	void showWarningICON(boolean key);//警告标志
	void showResultsInfo(String result,String time,int sampleNumber);//显示多样品
}
