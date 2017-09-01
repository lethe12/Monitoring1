package cn.com.grean.Presenter;

import cn.com.grean.model.DetailAdvanced;
import cn.com.grean.model.DetailAdvancedModel;
import cn.com.grean.monitoring.AdvancedFragmentView;

public class AdvanceDetail implements DetailAdvancedPresenter{
	private DetailAdvancedModel model = new DetailAdvanced();
	private AdvancedFragmentView view;
	
	public AdvanceDetail(AdvancedFragmentView advancedFragmentView) {
		// TODO 自动生成的构造函数存根
		view = advancedFragmentView;
	}

	@Override
	public void showDetail() {
		// TODO 自动生成的方法存根
		if (view!=null) {
			float [] params = model.getCalibrationParams();			
			String uri = model.getUpdataUri();
			view.showDetail(params, model.getNowDevicesName(), model.loadCommunicationProtocol(),model.getDevicesRange(),uri);
		}
	}

	@Override
	public String[] getScriptNames() {
		// TODO 自动生成的方法存根
		return model.getScriptNameStrings();
	}

	@Override
	public String[] getDeviceNames() {
		// TODO 自动生成的方法存根
		return model.getDevicesNameStrings();
	}

	@Override
	public String[] getCommunicationProtocolNames() {
		// TODO 自动生成的方法存根
		return model.getCommunicationProtocolStrings();
	}

	@Override
	public String[] getVirtualDevicesNames() {
		// TODO 自动生成的方法存根
		return model.getVirtualDeviceStrings();
	}

	@Override
	public boolean loadScript(int pos,int range) {
		// TODO 自动生成的方法存根
		return model.loadScript(pos,range);
	}

	@Override
	public void interviewScript(int pos) {
		// TODO 自动生成的方法存根
		String [] list = model.getScriptContentStrings(pos);
		if (view!=null) {
			view.showScriptContent(list);
		}
	}

	@Override
	public int getNowDevices() {
		// TODO 自动生成的方法存根
		return model.getNowDevicesName();
	}

	@Override
	public int getNowCommunictionProtocol() {
		// TODO 自动生成的方法存根
		return getNowCommunictionProtocol();
	}

	@Override
	public void setVirtualDevices(int pos, String params) {
		// TODO 自动生成的方法存根
		model.setVirtualDevice(pos, params);
	}

	@Override
	public void enableVirtualDevices() {
		// TODO 自动生成的方法存根
		model.enableVirtualDevice();
	}

	@Override
	public boolean saveCalParams(float[] params) {
		// TODO 自动生成的方法存根
		return model.setCalibrationParams(params);
	}

	@Override
	public void savePassword(String password) {
		// TODO 自动生成的方法存根
		model.changMangerPassword(password);
	}

	@Override
	public void setChooseDevices(int pos) {
		// TODO 自动生成的方法存根
		model.setNowDevices(pos);
	}

	@Override
	public void setChooseProtocol(int pos) {
		// TODO 自动生成的方法存根
		model.setCommunicationProtocol(pos);
	}

	@Override
	public void saveUpdataURI(String uri) {
		// TODO 自动生成的方法存根
		model.setUpdataUri(uri);
	}

	@Override
	public String[] getDevicesRanges() {
		// TODO 自动生成的方法存根
		return model.getRangesStrings();
	}

	@Override
	public int getDevicesRange() {
		// TODO 自动生成的方法存根
		int temp = model.getDevicesRange();
		if (temp >= (model.getDevicesNameStrings().length)) {
			temp = 0;
		}
		return temp;
	}

	@Override
	public void setDevicesRange(int range) {
		// TODO 自动生成的方法存根
		model.setDeviesRange(range);
	}

	@Override
	public String getVirtualDevices(int pos) {
		// TODO 自动生成的方法存根
		return model.getVirtualDevices(pos);
	}

}
