package cn.com.grean.model;

import cn.com.grean.RS485;
import cn.com.grean.myApplication;
import cn.com.grean.protocol.ProtocolProcessorImp;

/**
 * 通讯模式下的数据格式
 * @author Administrator
 *
 */
public class CommunicationInfo {
	private boolean MasterMode = false;//主机模式
	private int slaveBaudRate = 9600;
	private byte slaveAddress = 0x01;
	private String TcpId;
	private String TcpIp;
	private int TcpPort;
	public void setMasterMode(boolean masterMode) {
		MasterMode = masterMode;
		myApplication.getInstance().putConfig("MasterMode", masterMode);
	}
	
	public CommunicationInfo() {
		// TODO 自动生成的构造函数存根
		myApplication app =myApplication.getInstance();
		MasterMode = app.getConfigBoolean("MasterMode");
		slaveBaudRate = app.getConfigInt("SlaveBaudRate");
		slaveAddress = (byte) app.getConfigInt("SlaveAddress");
		setTcpId(app.getConfigString("TCPID"));
		setTcpIp(app.getConfigString("TCPIP"));
		setTcpPort(app.getConfigInt("TCPPORT"));
	}
	public boolean isMasterMode() {
		return MasterMode;
	}
	public int getSlaveBaudRate() {
		return slaveBaudRate;
	}
	public byte getSlaveAddress() {
		return slaveAddress;
	}
	
	
	
	public void setSlaveCommunication(int baudRate,int address){
		slaveAddress = (byte) address;
		slaveBaudRate = baudRate;
		myApplication app =myApplication.getInstance();
		app.putConfig("SlaveBaudRate", baudRate);
		app.putConfig("SlaveAddress", address);
		ProtocolProcessorImp.getInstance().setByteProtocolAddress((byte) address);
		RS485.getInstance().setSeriPort(baudRate);
	}

	public String getTcpId() {
		return TcpId;
	}

	public void setTcpId(String tcpId) {
		TcpId = tcpId;
	}

	public String getTcpIp() {
		return TcpIp;
	}

	public void setTcpIp(String tcpIp) {
		TcpIp = tcpIp;
	}

	public int getTcpPort() {
		return TcpPort;
	}

	public void setTcpPort(int tcpPort) {
		TcpPort = tcpPort;
	}

}
