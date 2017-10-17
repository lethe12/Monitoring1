package cn.com.grean;

import cn.com.grean.protocol.ProtocolProcessorImp;
import android.util.Log;

public class RS485 extends SerialPortCommunication{
	private final static String tag = "RS485"; 
	private static RS485 instance = new RS485();
	private boolean CommunicateEnable = true;
	//private RS485MsgListener msgListener;
	//private DeviceControlPanel panel;
	//private byte [] dataBuffer = new byte[4];//用于存储计算结果寄存器
	private byte address = 0x01;
	public void setAddress(byte address) {
		this.address = address;
	}
	
	private RS485() {
		// TODO 自动生成的构造函数存根		
		super(3, 8, 9600, 1, 'n');
	}

	public void setSeriPort(int BDrate){
		ChangeSerialPort(3,8,BDrate,1,'n');
	}
	
	@Override
	protected Boolean handleBuffer() {
		// TODO 自动生成的方法存根
		if (size > 6) {
			return true;
		}
		else {
			return false;
		}
	}

	/*private void RS485Protocol(byte []rec,int len){
		Log.d(tag, "收到");
		if (msgListener != null) {
			String text = tools.bytesToHexString(rec, len);
			msgListener.onReceiveOneMsg(text);
		}
		else {
			Log.d(tag, "无监听");
		}
		
		int reg,data;
		reg = tools.byte2int(rec, 2);
		data = tools.byte2int(rec, 4);
		switch (rec[1]) {
		case 0x06://写
			if (reg == 0) {//控制
				if (panel != null) {					
					switch (data) {
					case 0:
						if (panel.isBusy()) {
							panel.setDeviceStop();							
						}
						break;
					case 1:
						if (panel.isBusy()) {
							
						}
						else {
							panel.setDeviceTest();
						}
						break;
					case 2:
						if (panel.isBusy()) {
							
						}
						else {
							panel.setDeviceCalibration();
						}
						break;
					case 3:
						if (panel.isBusy()) {
							
						}
						else {
							panel.setDeviceTestStandard();
						}
						break;
					case 4:
						if (panel.isBusy()) {
							
						}
						else {
							panel.setDeviceInit();
						}
						break;
					default:
						break;
					}
				}
			}
			else {//超范围
				
			}
			break;
		case 0x03:
		case 0x04:
			if (reg>0) {
				byte[] cmd = new byte [data*2+5]; 
				cmd[0] = rec[0];
				cmd[1] = rec[1];
				cmd[2] = (byte) (data * 2);
				for (int i = 0; i < data; i++) {
					if ((reg+i)<3) {
						cmd[3+i*2] = dataBuffer[reg+i-1];
						cmd[4+i*2] = dataBuffer[reg+i];
					}
					else {						
						cmd[3+i*2] = 0x00;
						cmd[4+i*2] = 0x00;
					}					
				}
				int crc = tools.calcCrc16(cmd, 0, data*2+3);
				byte [] crcBuff = tools.int2byte(crc);
				cmd[data*2+3] = crcBuff[0];
				cmd[data*2+4] = crcBuff[1];
				sendBuff.add(cmd);
			}
			else {
				
			}
			break;

		default:
			break;
		}
	}*/
	
	@Override
	protected void CommunicationProtocal(byte[] rec, int len) {
		// TODO 自动生成的方法存根
		if (CommunicateEnable) {			
			byte [] cmd  = ProtocolProcessorImp.getInstance().BytesProtocol(rec, len);
			//RS485Protocol(rec,len);
			if (cmd!=null) {
				sendBuff.add(cmd);
			}
		}
	}

	@Override
	protected void AsynchronousCommunicationProtocal(byte[] rec, int len) {
		// TODO 自动生成的方法存根
		if (CommunicateEnable) {			
			byte [] cmd  = ProtocolProcessorImp.getInstance().BytesProtocol(rec, len);
			//RS485Protocol(rec,len);
			if (cmd!=null) {
				sendBuff.add(cmd);
			}
		}
	}

	public static RS485 getInstance() {
		return instance;
	}
	/**
	 * 使能通讯，
	 * @return true 可通讯，处于非维护状态 ；false 不能通讯 处于维护状态 
	 */
	public boolean isCommunicateEnable() {
		return CommunicateEnable;
	}

	public void setCommunicateEnable(boolean communicateEnable) {
		CommunicateEnable = communicateEnable;
	}

}
