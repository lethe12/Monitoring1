package cn.com.grean.protocol;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.model.HistoryDataFormat;
import cn.com.grean.protocol.GeneralBytesProtocol.ControlProtocol;

public class TCPASCIIProtocol implements GeneralASCIIProtocol{
	private final static String tag = "TCPASCIIProtocol";
	float result;
	long date;
	private String heartString = "www.grean.com.cn";
	private String id="Grean";

	public TCPASCIIProtocol() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public String ProcessProtocol(String rec, ControlProtocol controlProtocol,ProtocolHistoryData data) {
		// TODO 自动生成的方法存根
		String [] tempStrings = rec.split(";");
		String busyString = "Device=Busy";
		for (int i = 0; i < tempStrings.length; i++) {
			String [] miniString = tempStrings[i].split("=");
			if (miniString.length == 2) {
				if (miniString[0].equals("Device")) {
					if (miniString[1].equals("Test")) {
						if (controlProtocol.onComplete(ControlProtocol.TEST)) {							
							return "Device=Testing";
						}else {
							return busyString;
						}
					}else if (miniString[1].equals("Calibrate")) {
						if (controlProtocol.onComplete(ControlProtocol.CALIBRATION)) {
							return "Device=Calibrating";
						}else {
							return busyString;
						}
					}else if (miniString[1].equals("QC")) {
						if (controlProtocol.onComplete(ControlProtocol.QUALITY_CONTROL)) {
							return "Device=QC";
						}else {
							return busyString;
						}
					}else if (miniString[1].equals("Initialize")) {
						if (controlProtocol.onComplete(ControlProtocol.INIT)) {
							return "Device=Initializing";
						}else {
							return busyString;
						}
					}else if (miniString[1].equals("State")) {
						if (controlProtocol.onComplete(ControlProtocol.STATE)) {
							return busyString;
						}else {
							return "Device=Idle";
						}
					}else if (miniString[1].equals("Stop")) {
						return "Device=Idle";
					}else if (miniString[1].equals("Result")) {
						return "Device="+tools.float2String3(result)+"mg/L;Date="+tools.timestamp2string(date);
					}else if (miniString[1].equals("HistoryData")) {
						long from = tools.nowtime2timestamp();
						long to = from-7*24*60*60000l;
						HistoryDataFormat dataFormat = data.onComplete(from, to);
						List<String> list =  new ArrayList<String>();
						dataFormat.transformDataText(list);
						String string = id+":";
						String tempString=" ";
						for (int j = 0; j <list.size(); j++) {
							/*try {
								tempString =Base64.encodeToString(list.get(j).getBytes("gbk"),Base64.DEFAULT);
							} catch (UnsupportedEncodingException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}// new String(list.get(j).getBytes(),"GBK");*/
							
							tempString = list.get(j);
							string = string+tempString+";";							
						}
						return string;
					}else if (miniString[1].equals("Log")) {
						long from = tools.nowtime2timestamp();
						long to = from-7*24*60*60000l;
						List<String> list = new ArrayList<String>();
						data.onCompleteLog(from, to,list);
						String string = id+":";
						String tempString=" ";
						for (int j = 0; j < list.size(); j++) {
							tempString = list.get(j);
							//Log.d(tag, "log="+tempString);
							string = string+tempString+";";									
						}
						return string;
					}else if(miniString[1].equals("CalibrationInfo")){
						return myApplication.getInstance().getCompute().getCalibrationInfo();
					}
					else {
						return "Unknow Command";
					}
				}
				else{
					return "Unknow Device";
				}
			}
			else {
				/*String t = "啊啊";
				try {
					//String utf8 = new String(t.getBytes( "UTF-8")); 
					//String unicode = new String(utf8.getBytes(),"UTF-8");   					
					//String gbk = new String(unicode.getBytes("GBK")); 
					
					
					//String gbk = URLEncoder.encode(t,"GBK");
					//String utf = URLDecoder.decode(t, "UTF-8");
					//String gbk = URLEncoder.encode(utf, "GBK");
					Log.d(tag, "Rec="+new String(rec.getBytes("GBK"), "UTF-8"));
					String gbk=new String(t.getBytes("UTF-8"), "GBK");
					//String gbk = new String(t.getBytes("UTF-8"),"ISO-8859-1");
					Log.d(tag, "TCP="+tools.bytesToHexString(gbk.getBytes(), gbk.getBytes().length));
					return gbk;
				} catch (UnsupportedEncodingException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				return t;*/
				return "Error Format";
			}
		}
		//return "啊";
		return "Error Format";
	}

	@Override
	public void setResult(float result) {
		// TODO 自动生成的方法存根
		this.result=result;
	}

	@Override
	synchronized public void setTimeStamp(long date) {
		// TODO 自动生成的方法存根
		this.date = date;
		heartString = "\n"+id+";TN="+tools.float2String3(result)+"mg/L;Date="+tools.timestamp2string(date)+"\n";
		
	}

	@Override
	public String getHeartString() {
		// TODO 自动生成的方法存根
		return heartString;
	}

	@Override
	synchronized public void setID(String id) {
		// TODO 自动生成的方法存根
		this.id = id;
		heartString = "\n"+id+";TN="+tools.float2String3(result)+"mg/L;Date="+tools.timestamp2string(date)+"\n";
	}

}
