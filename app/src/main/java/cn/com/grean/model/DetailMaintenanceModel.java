package cn.com.grean.model;

import cn.com.grean.EquipmentData;
import cn.com.grean.script.instruction.DevicesData;

public interface DetailMaintenanceModel {
	public String getParams();//一键获取ch1~9参数
	public DevicesData getDevices();//获取泵阀状态
	public void setValveOn(int num);//开阀
	public void setValveOff(int num);//关阀
	public void calTemp(int ch,float data);
	public void startPump(int num,int round,int time);
	public void stopPump(int num);
	public void setPumpParams(int num,int params);
	public boolean initDevices();
	public boolean clearDevices();
	public void setMaintenanceModel(boolean key);//开关维护模式
	public EquipmentData loadEquipmentData();
	public void ctrlVD(int num ,boolean key);
}
