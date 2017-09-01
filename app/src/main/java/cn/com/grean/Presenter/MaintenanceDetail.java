package cn.com.grean.Presenter;

import cn.com.grean.EquipmentData;
import cn.com.grean.model.DetailMaintenance;
import cn.com.grean.model.DetailMaintenanceModel;
import cn.com.grean.monitoring.MaintenanceDetailView;
import cn.com.grean.script.instruction.DevicesData;

public class MaintenanceDetail implements DetailMaintenancePresenter{
	@SuppressWarnings("unused")
	private final static String tag = "DetailMaintenance";
	private MaintenanceDetailView view;
	private DetailMaintenanceModel model = new DetailMaintenance();
	public MaintenanceDetail(MaintenanceDetailView maintenanceDetailView) {
		// TODO 自动生成的构造函数存根
		this.view = maintenanceDetailView;
	}

	@Override
	public void setValve(int num, boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			model.setValveOn(num);
		}
		else {
			model.setValveOff(num);
		}
	}

	@Override
	public void startPump(int num, int round, int time) {
		// TODO 自动生成的方法存根
		model.startPump(num, round, time);
	}

	@Override
	public void stopPump(int num) {
		// TODO 自动生成的方法存根
		model.stopPump(num);
	}

	@Override
	public void setPumpParams(int num, float params) {
		// TODO 自动生成的方法存根
		int p = (int)(params*100f);
		model.setPumpParams(num, p);
	}

	@Override
	public String getParams() {
		// TODO 自动生成的方法存根
		return model.getParams();
	}

	@Override
	public DevicesData getDevices() {
		// TODO 自动生成的方法存根
		return model.getDevices();
	}

	@Override
	public void calTemp(int ch, float data) {
		// TODO 自动生成的方法存根
		model.calTemp(ch, data);
	}

	@Override
	public void showInfo() {
		// TODO 自动生成的方法存根
		EquipmentData data = model.loadEquipmentData();
		if (view != null) {
			view.showMaintenanceDetail(data);
			view.showMaintenanceIcon(data.isMaintance());
		}
	}

	@Override
	public boolean init() {
		// TODO 自动生成的方法存根
		return model.initDevices();
	}

	@Override
	public boolean clear() {
		// TODO 自动生成的方法存根
		return model.clearDevices();
	}

	@Override
	public void MaintenanceModelSwitch(boolean key) {
		// TODO 自动生成的方法存根
		model.setMaintenanceModel(key);
		if (view != null) {			
			view.showMaintenanceIcon(key);
		}

	}

	@Override
	public void setVirtual(int num, boolean key) {
		// TODO 自动生成的方法存根
		model.ctrlVD(num, key);
	}

}
