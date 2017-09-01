package cn.com.grean.monitoring;

import cn.com.grean.EquipmentData;
import cn.com.grean.script.instruction.DevicesData;

public interface MaintenanceDetailView {
	void showParams(String data);//刷新通道1~9
	void showDevices(DevicesData data);//刷新泵阀状态
	void showMaintenanceDetail(EquipmentData data);//显示静态信息
	/**
	 * 显示主界面图标，通过intend
	 * @param key =true 显示泵阀操作 =false 关闭细节操作
	 */
	void showMaintenanceIcon(boolean key);
}
