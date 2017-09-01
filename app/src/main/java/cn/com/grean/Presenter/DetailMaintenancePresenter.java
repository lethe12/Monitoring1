package cn.com.grean.Presenter;

import cn.com.grean.script.instruction.DevicesData;

public interface DetailMaintenancePresenter {
	void setValve(int num,boolean key);//开关电磁阀
	void setVirtual(int num ,boolean key);
	void startPump(int num,int round,int time);//蠕动泵校准
	void stopPump(int num);//停泵
	void setPumpParams(int num,float params);//设置蠕动泵参数
	String getParams();//获取测量系数
	DevicesData getDevices();//获取泵阀状态
	void calTemp(int ch,float data);//校准温度系数
	void showInfo();//显示静态信息
	void MaintenanceModelSwitch(boolean key);
	boolean init();
	boolean clear();
}
