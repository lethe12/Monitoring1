package cn.com.grean.protocol;

import java.util.List;

import android.util.Log;

import cn.com.grean.DbTask;
import cn.com.grean.DeviceControlPanel;
import cn.com.grean.RS485;
import cn.com.grean.RS485MsgListener;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.model.HistoryDataFormat;
import cn.com.grean.protocol.GeneralBytesProtocol.ControlProtocol;

public class ProtocolProcessorImp implements ProtocolProcessor,ControlProtocol,ProtocolHistoryData{
	private static final String tag = "ProtocolProcessorImp";
	private static ProtocolProcessorImp instance = new ProtocolProcessorImp();
	private DeviceControlPanel panel=null;
	private RS485MsgListener msgListener=null;
	private GeneralBytesProtocol bytesProtocol;
	private GeneralASCIIProtocol asciiProtocol;
	public void setPanel(DeviceControlPanel panel) {
		this.panel = panel;
	}

	private ProtocolProcessorImp() {
		// TODO 自动生成的构造函数存根
	}

	public static ProtocolProcessorImp getInstance() {
		return instance;
	}

	@Override
	public byte[] BytesProtocol(byte[] rec, int len) {
		// TODO 自动生成的方法存根
		if (msgListener!=null) {
			String text = tools.bytesToHexString(rec, len);
			msgListener.onReceiveOneMsg(text);
		}
		getGeneralByteProtocol();
		return bytesProtocol.ProcessProtocol(rec, len, this);
	}

	@Override
	public String ASCIIProtocol(String rec) {
		// TODO 自动生成的方法存根
		getGeneralASCIIProtocol();
		return asciiProtocol.ProcessProtocol(rec, this,this);
	}

	@Override
	public void setProtocolResult(float result) {
		// TODO 自动生成的方法存根
		getGeneralByteProtocol();
		bytesProtocol.setNowResult(result);
		getGeneralASCIIProtocol();
		asciiProtocol.setResult(result);
	}

	public void setMsgListener(RS485MsgListener msgListener) {
		this.msgListener = msgListener;
	}

	@Override
	public String getHeartString() {
		// TODO 自动生成的方法存根
		getGeneralASCIIProtocol();
		return asciiProtocol.getHeartString();
	}

	@Override
	public boolean onComplete(int cmd) {
		// TODO 自动生成的方法存根
		if (panel!=null) {
			if (panel.isBusy()) {
				if (cmd==ControlProtocol.STOP) {
					panel.setDeviceStop();
					return true;
				}
				else if (cmd==ControlProtocol.STATE) {
					return true;
				}
				return false;
			}
			else {
				
				switch (cmd) {
				case ControlProtocol.CALIBRATION:
					panel.setDeviceCalibration();
					break;
				case ControlProtocol.QUALITY_CONTROL:
					panel.setDeviceTestStandard();
					break;			
				case ControlProtocol.TEST:
					panel.setDeviceTest();
					break;
				case ControlProtocol.INIT:
					panel.setDeviceInit();
					break;
				case ControlProtocol.STATE:
					return false;
				default:
					return false;
				}
				return true;
			}
		}
		return false;
	}

	private void getGeneralByteProtocol(){
		if (bytesProtocol==null) {
			String name = myApplication.getInstance().getConfigString("CommunicationProtocol");
			if (name.equals("ModBus")) {
				bytesProtocol = new ModBusByteProtocol();
			}
			else {
				bytesProtocol = new ModBusByteProtocol();
			}
		}
	}
	
	private void getGeneralASCIIProtocol(){
		if (asciiProtocol == null) {
			asciiProtocol = new TCPASCIIProtocol();
		}
	}

	@Override
	public byte[] getStartFrame() {
		// TODO 自动生成的方法存根		
		getGeneralByteProtocol();
		return bytesProtocol.getStartFrame();
	}

	@Override
	public byte[] getInquireFrame() {
		// TODO 自动生成的方法存根		
		getGeneralByteProtocol();
		return bytesProtocol.getInquireFrame();
	}

	@Override
	public byte[] getReturnFrame() {
		// TODO 自动生成的方法存根
		getGeneralByteProtocol();
		return bytesProtocol.getReturnFrame();
	}

	@Override
	public void setByteProtocolAddress(byte address) {
		// TODO 自动生成的方法存根
		getGeneralByteProtocol();
		RS485.getInstance().setAddress(address);
		bytesProtocol.setModBusAddress(address);
	}

	@Override
	public float getProtocolResult() {
		// TODO 自动生成的方法存根
		getGeneralByteProtocol();
		return bytesProtocol.getNowResult();
	}

	@Override
	public void setProtocolTimeStamp(long date) {
		// TODO 自动生成的方法存根
		getGeneralASCIIProtocol();
		asciiProtocol.setTimeStamp(date);
	}

	@Override
	public void setASCIIProtocolID(String id) {
		// TODO 自动生成的方法存根
		getGeneralASCIIProtocol();
		asciiProtocol.setID(id);
	}

	@Override
	public HistoryDataFormat onComplete(long from, long to) {
		// TODO 自动生成的方法存根
		String statement;
		if (from>to) {
			statement = "date<" +String.valueOf(from)+" and date>"+String.valueOf(to);
		}
		else{
			statement = "date>" +String.valueOf(from)+" and date<"+String.valueOf(to);
		}
		return myApplication.getInstance().loadHistoryData(statement);
	}

	@Override
	public void onCompleteLog(long from, long to,List<String> list) {
		// TODO 自动生成的方法存根
		String statement;
		if (from>to) {
			statement = "date<" +String.valueOf(from)+" and date>"+String.valueOf(to);
		}
		else{
			statement = "date>" +String.valueOf(from)+" and date<"+String.valueOf(to);
		}
		//Log.d(tag, tools.timestamp2string(from)+";"+tools.timestamp2string(to));
		myApplication.getInstance().loadDataBase("log", statement, list, DbTask.LOG_CONTENT,DbTask.LOG_DATE);
	}	
	
}
