package cn.com.grean.Presenter;

import android.util.Log;
import cn.com.grean.RS485;
import cn.com.grean.TCPListeners;
import cn.com.grean.model.DetailCommunication;
import cn.com.grean.model.DetailCommunicationModel;
import cn.com.grean.monitoring.CommunicationFragmentView;
import cn.com.grean.protocol.ProtocolProcessorImp;

public class CommunicationDetail implements DetailCommunicationPresenter,TCPListeners{
	private final static String tag = "CommunicationDetail";
	private DetailCommunicationModel model =new DetailCommunication();
	private CommunicationFragmentView view;

	public CommunicationDetail(CommunicationFragmentView communicationFragmentView) {
		// TODO 自动生成的构造函数存根
		view = communicationFragmentView;
	}

	@Override
	public void showDetail() {
		// TODO 自动生成的方法存根
		if (view!=null) {
			view.showCommunicationInfo(model.loadCommunicationInfo());
		}
	}

	@Override
	public String[] getSlaveBaudRate() {
		// TODO 自动生成的方法存根
		return model.getSpinnerString();
	}

	@Override
	public void setSlaveBaudRate(int position) {
		// TODO 自动生成的方法存根
		String string = model.getBaudRateString(position);
		model.setSlaveBaudRate(Integer.valueOf(string));
	}

	@Override
	public boolean setSlveAddress(int address) {
		// TODO 自动生成的方法存根
		Log.d(tag, String.valueOf(address));
		return model.setSlaveAddress(address);
	}

	

	@Override
	public int getDefaultSlaveBaudRate() {
		// TODO 自动生成的方法存根
		return model.getDefaultSlaveBaudRate();
	}

	@Override
	public void setDefaultValue(float value) {
		// TODO 自动生成的方法存根
		model.setDefaultValue(value);
		String text = "启动命令:"+model.getStartString()+"\n数据查询:"+model.getInquireString()+"\n数据返回:"+model.getRequireString();
		if (view!=null) {
			view.updataTestCmd(text);
		}
	}

	@Override
	public void returnDefault() {
		// TODO 自动生成的方法存根
		model.rollBackDefaultValue();
		ProtocolProcessorImp.getInstance().setMsgListener(null);
	}

	@Override
	public void setCommunicationMode(boolean key) {
		// TODO 自动生成的方法存根
		model.setCommunicationMode(key);
		view.updataCommunicationMode(key);
	}

	

	@Override
	public boolean isTCPConnected() {
		// TODO 自动生成的方法存根
		return model.isConnected();
	}

	@Override
	public void onComplete(boolean connected) {
		// TODO 自动生成的方法存根
		if (view!=null) {
			view.showTCPConnect(connected);
		}
	}

	@Override
	public void setTCPConnect(String IP, int port, String id, boolean enable) {
		// TODO 自动生成的方法存根
		if (enable) {
			model.setTCPID(id);
			model.setTCPConnect(IP, port,this);
		}
		else {
			model.setTCPDisconnect();
		}
	}

}
