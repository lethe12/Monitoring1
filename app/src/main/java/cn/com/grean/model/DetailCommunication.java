package cn.com.grean.model;

import android.util.Log;
import cn.com.grean.TCPListeners;
import cn.com.grean.TCPTask;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.protocol.ProtocolProcessorImp;

public class DetailCommunication implements DetailCommunicationModel{
	private final static String tag = "DetailCommunication";
	private String [] BaudRate = new String[]{"2400","4800","9600","14400","19200","38400"};
	private CommunicationInfo info = new CommunicationInfo();
	private int baudRate;
	private boolean hasSetDefault = false;
	private float tempValue;

	public DetailCommunication() {
		// TODO 自动生成的构造函数存根
		baudRate = info.getSlaveBaudRate();
	}

	@Override
	public CommunicationInfo loadCommunicationInfo() {
		// TODO 自动生成的方法存根
		return info;
	}

	@Override
	public String[] getSpinnerString() {
		// TODO 自动生成的方法存根
		return BaudRate;
	}

	@Override
	public String getBaudRateString(int index) {
		// TODO 自动生成的方法存根		
		return BaudRate[index];
	}

	

	@Override
	public void setCommunicationMode(boolean key) {
		// TODO 自动生成的方法存根
		info.setMasterMode(key);
	}
	

	@Override
	public void setSlaveBaudRate(int BaudRate) {
		// TODO 自动生成的方法存根
		this.baudRate = BaudRate;
	}

	@Override
	public boolean setSlaveAddress(int address) {
		// TODO 自动生成的方法存根
		Log.d(tag, String.valueOf(address));
		if ((address >0)&&(address <255)) {
			info.setSlaveCommunication(baudRate, address);
			myApplication.getInstance().putConfig("SLaveAddress", address);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int getDefaultSlaveBaudRate() {
		// TODO 自动生成的方法存根

		switch (info.getSlaveBaudRate()) {
		case 2400:
			return 0;
		case 4800:
			return 1;
		case 9600:
			return 2;
		case 14400:
			return 3;
		case 19200:
			return 4;
		case 38400:
			return 5;
		default:
			return 2;
		}

	}

	@Override
	public void setDefaultValue(float value) {
		// TODO 自动生成的方法存根
		hasSetDefault = true;
		tempValue = ProtocolProcessorImp.getInstance().getProtocolResult();
		ProtocolProcessorImp.getInstance().setProtocolResult(value);
	}

	@Override
	public void rollBackDefaultValue() {
		// TODO 自动生成的方法存根
		if (hasSetDefault) {
			ProtocolProcessorImp.getInstance().setProtocolResult(tempValue);
		}
	}

	@Override
	public String getStartString() {
		// TODO 自动生成的方法存根
		return tools.bytesToHexString(ProtocolProcessorImp.getInstance().getStartFrame(), 8);
	}

	@Override
	public String getInquireString() {
		// TODO 自动生成的方法存根
		return tools.bytesToHexString(ProtocolProcessorImp.getInstance().getInquireFrame(), 8);
	}

	@Override
	public String getRequireString() {
		// TODO 自动生成的方法存根
		 return tools.bytesToHexString(ProtocolProcessorImp.getInstance().getReturnFrame(), 9);
	}

	@Override
	public boolean isConnected() {
		// TODO 自动生成的方法存根
		return TCPTask.getInstance().isConnected();
	}

	@Override
	public void setTCPDisconnect() {
		// TODO 自动生成的方法存根
		TCPTask.getInstance().setTCPDisconnect();
	}

	@Override
	public void setTCPConnect(String IP, int port, TCPListeners listeners) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().putConfig("TCPIP", IP);
		myApplication.getInstance().putConfig("TCPPORT", port);
		TCPTask.getInstance().setTCPConnect(IP, port, listeners);
	}

	@Override
	public void setTCPID(String id) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().putConfig("TCPID", id);
		ProtocolProcessorImp.getInstance().setASCIIProtocolID(id);
	}
}
