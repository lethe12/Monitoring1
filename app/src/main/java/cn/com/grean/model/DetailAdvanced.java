package cn.com.grean.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cn.com.grean.RS232;
import cn.com.grean.myApplication;
import cn.com.grean.script.ScriptContent;

import android.util.Log;

public class DetailAdvanced implements DetailAdvancedModel{
	private final static String tag = "DetailAdvanced";
	private final static String [] scriptNameStrings = new String [] {"测量脚本","高点校准","低点校准","初始化","清洗脚本","维护脚本","测量标液"};
	private final static String [] scriptTableNames = new String []{"measurescript","hcalibratscript","lcalibratscript","initscript","clearscript","vindicatescript","qualityscript","script"};
	private final static String [] devicesNames = new String[]{"总氮","总磷","氨氮","草甘膦","微囊藻毒素"};
	private final static String [] communicationProtocolString = new String []{"ModBus","ModBusForLab","ASCII"};
	private ArrayList<String> scriptArrayList = new ArrayList<String>();//当前脚本集合
	private String uri;
	
	public DetailAdvanced() {
		// TODO 自动生成的构造函数存根
		uri = myApplication.getInstance().getConfigString("URL");
		if (uri=="") {
			uri="http://192.168.168.61:12599/Monitoring.apk";
		}
	}

	@Override
	public String[] getScriptNameStrings() {
		// TODO 自动生成的方法存根
		return scriptNameStrings;
	}

	@Override
	public boolean loadScript(int name,int range) {
		// TODO 自动生成的方法存根
		String nameString;
		String tablename;
		//Log.d(tag, "range="+String.valueOf(range));
		switch (name) {
		case 0:
			switch (range) {
			case 1:
				nameString = "/storage/usbhost1/test1-2.txt";
				tablename = "measurescript2";
				break;
			case 2:
				nameString = "/storage/usbhost1/test1-3.txt";
				tablename = "measurescript3";
				break;
			case 3:
				nameString = "/storage/usbhost1/test1-4.txt";
				tablename = "measurescript4";
				break;
			case 4:
				nameString = "/storage/usbhost1/test1-5.txt";
				tablename = "measurescript5";
				break;
			case 5:
				nameString = "/storage/usbhost1/test1-6.txt";
				tablename = "measurescript6";
				break;
			case 6:
				nameString = "/storage/usbhost1/test1-7.txt";
				tablename = "measurescript7";
				break;
			case 7:
				nameString = "/storage/usbhost1/test1-8.txt";
				tablename = "measurescript8";
				break;
			default:
				nameString = "/storage/usbhost1/test1.txt";
				tablename = "measurescript";
				break;
			}
			
			break;
		case 1:
			switch (range) {
			case 1:
				nameString = "/storage/usbhost1/test2-2.txt";
				tablename = "hcalibratscript2";
				break;
			case 2:
				nameString = "/storage/usbhost1/test2-3.txt";
				tablename = "hcalibratscript3";
				break;
			case 3:
				nameString = "/storage/usbhost1/test2-4.txt";
				tablename = "hcalibratscript4";
				break;
			case 4:
				nameString = "/storage/usbhost1/test2-5.txt";
				tablename = "hcalibratscript5";
				break;
			case 5:
				nameString = "/storage/usbhost1/test2-6.txt";
				tablename = "hcalibratscript6";
				break;
			case 6:
				nameString = "/storage/usbhost1/test2-7.txt";
				tablename = "hcalibratscript7";
				break;
			case 8:
				nameString = "/storage/usbhost1/test2-8.txt";
				tablename = "hcalibratscript8";
				break;
			default:
				nameString = "/storage/usbhost1/test2.txt";
				tablename = "hcalibratscript";
				break;
			}
			
			break;
		case 2:
			switch (range) {
			case 1:
				nameString = "/storage/usbhost1/test3-2.txt";
				tablename = "lcalibratscript2";
				break;
			case 2:
				nameString = "/storage/usbhost1/test3-3.txt";
				tablename = "lcalibratscript3";
				break;
			case 3:
				nameString = "/storage/usbhost1/test3-4.txt";
				tablename = "lcalibratscript4";
				break;
			case 4:
				nameString = "/storage/usbhost1/test3-5.txt";
				tablename = "lcalibratscript5";
				break;
			case 5:
				nameString = "/storage/usbhost1/test3-6.txt";
				tablename = "lcalibratscript6";
				break;
			case 6:
				nameString = "/storage/usbhost1/test3-7.txt";
				tablename = "lcalibratscript7";
				break;
			case 7:
				nameString = "/storage/usbhost1/test3-8.txt";
				tablename = "lcalibratscript8";
				break;

			default:
				nameString = "/storage/usbhost1/test3.txt";
				tablename = "lcalibratscript";
				break;
			}
			break;
		case 3:
			nameString = "/storage/usbhost1/test4.txt";
			tablename = "initscript";
			break;
		case 4:
			nameString = "/storage/usbhost1/test5.txt";
			tablename = "clearscript";
			break;
		case 5:
			nameString = "/storage/usbhost1/test6.txt";
			tablename = "vindicatescript";
			break;
		case 6:
			switch (range) {
			case 1:
				nameString = "/storage/usbhost1/test7-2.txt";
				tablename = "qualityscript2";
				break;
			case 2:
				nameString = "/storage/usbhost1/test7-3.txt";
				tablename = "qualityscript3";
				break;
			case 3:
				nameString = "/storage/usbhost1/test7-4.txt";
				tablename = "qualityscript4";
				break;
			case 4:
				nameString = "/storage/usbhost1/test7-5.txt";
				tablename = "qualityscript5";
				break;
			case 5:
				nameString = "/storage/usbhost1/test7-6.txt";
				tablename = "qualityscript6";
				break;
			case 6:
				nameString = "/storage/usbhost1/test7-7.txt";
				tablename = "qualityscript7";
				break;
			case 7:
				nameString = "/storage/usbhost1/test7-8.txt";
				tablename = "qualityscript8";
				break;

			default:
				nameString = "/storage/usbhost1/test7.txt";
				tablename = "qualityscript";
				break;
			}
			break;
		default:
			tablename = "script";
			nameString = "/storage/usbhost1/test6.txt";
			break;
		}
		File file = new File(nameString);
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String readline = "";
				StringBuffer sb = new StringBuffer();
	
				scriptArrayList.clear();
				while ((readline = br.readLine()) != null) {//一次读取一行
					scriptArrayList.add(readline);
					sb.append(readline);
				}
			
				br.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			//Log.d(tag, "文件大小"+String.valueOf(scriptArrayList.size()));			
			myApplication.getInstance().updataDataBase(tablename,scriptArrayList);
			ScriptContent.getInstance().clearScriptName();
			return true;
		}
		else {
			return false;
		}
		
		
	}

	@Override
	public String[] getScriptContentStrings(int name) {
		// TODO 自动生成的方法存根
		Log.d(tag,scriptTableNames[name]);
		if ((name <3)||(name==6)) {	//测量、低标、高标 脚本
			int range = myApplication.getInstance().getDevicesRange();//获取量程
			if ((range >0)&&(range<8)) {
				String tableName = scriptTableNames[name]+String.valueOf(range+1);
				return myApplication.getInstance().readDataBase(tableName);
			}
			else {
				
				return myApplication.getInstance().readDataBase(scriptTableNames[name]);
			}
		}else if (name <6) {
			return myApplication.getInstance().readDataBase(scriptTableNames[name]);
		}else {
			return null;
		}
	}

	@Override
	public String[] getDevicesNameStrings() {
		// TODO 自动生成的方法存根
		return devicesNames;
	}

	@Override
	public int getNowDevicesName() {
		// TODO 自动生成的方法存根
		String nameString = myApplication.getInstance().getDevicesName();
		if (nameString.equals("TN")) {
			return 0;
		}
		else if (nameString.equals("TP")) {
			return 1;
		}
		else if (nameString.equals("NH4")) {
			return 2;
		}
		else if (nameString.equals("Glyphosate")) {
			return 3;
		}
		else if (nameString.equals("Microcystis")) {
			return 4;
		}
		else {			
			return 0;
		}
	}

	@Override
	public String[] getCommunicationProtocolStrings() {
		// TODO 自动生成的方法存根
		return communicationProtocolString;
	}

	

	@Override
	public void setCommunicationProtocol(int name) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().putConfig("CommunicationProtocol", communicationProtocolString[name]);
	}

	@Override
	public String[] getVirtualDeviceStrings() {
		// TODO 自动生成的方法存根
		return myApplication.getInstance().getEquipmentInfo().getVirtualDevicesString();
	}

	@Override
	public void setVirtualDevice(int name, String params) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().getEquipmentInfo().setVirtualDevices(name, params, RS232.getInstance());
	}

	@Override
	public void enableVirtualDevice() {
		// TODO 自动生成的方法存根
		byte [] data = myApplication.getInstance().getEquipmentInfo().getEnableVirtualDevicesCmd();
		RS232.getInstance().Send(data);
	}

	@Override
	public float[] getCalibrationParams() {
		// TODO 自动生成的方法存根
		myApplication app =myApplication.getInstance();
		float [] params = new float [4];
		params[0] = app.getConfigFloat("slopeMin");
		params[1] = app.getConfigFloat("slopeMax");
		params[2] = app.getConfigFloat("interceptMin");
		params[3] = app.getConfigFloat("interceptMax");
		Log.d(tag, "Read"+String.valueOf(params[0])+";"+String.valueOf(params[1])+";"+String.valueOf(params[2])+";"+String.valueOf(params[3])+";");
		return params;
	}

	@Override
	public boolean setCalibrationParams(float[] params) {
		// TODO 自动生成的方法存根
		if (params.length == 4) {
			if ((params[1]<params[0])||(params[3]<params[2])) {
				return false;
			}
			Log.d(tag, "save"+String.valueOf(params[0])+";"+String.valueOf(params[1])+";"+String.valueOf(params[2])+";"+String.valueOf(params[3])+";");
			
			myApplication app =myApplication.getInstance();
			app.putConfig("slopeMin", params[0]);
			app.putConfig("slopeMax", params[1]);
			app.putConfig("interceptMin", params[2]);
			app.putConfig("interceptMax", params[3]);			
			return true;
		}
		else {			
			return false;
		}
	}

	@Override
	public void changMangerPassword(String password) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().putConfig("PassWord", password);
	}

	@Override
	public int loadCommunicationProtocol() {
		// TODO 自动生成的方法存根
		String string = myApplication.getInstance().getConfigString("CommunicationProtocol");
		if (string.equals(communicationProtocolString[0])) {
			return 0;
		}
		else if (string.equals(communicationProtocolString[1])) {
			return 1;
		}
		else if (string.equals(communicationProtocolString[2])) {
			return 2;
		}
		else {
			
			return 0;
		}
	}

	@Override
	public void setNowDevices(int pos) {
		// TODO 自动生成的方法存根
		switch (pos) {
		case 0:
			myApplication.getInstance().putConfig("DevicesName", "TN");
			break;
		case 1:
			myApplication.getInstance().putConfig("DevicesName", "TP");
			break;
		case 2:
			myApplication.getInstance().putConfig("DevicesName", "NH4");
			break;
		case 3:
			myApplication.getInstance().putConfig("DevicesName", "Glyphosate");
			break;
		case 4:
			myApplication.getInstance().putConfig("DevicesName", "Microcystis");
			break;
		default:
			myApplication.getInstance().putConfig("DevicesName", "TN");
			break;
		}
	}

	@Override
	public boolean setUpdataUri(String uri) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().putConfig("URL", uri);
		return false;
	}

	@Override
	public String getUpdataUri() {
		// TODO 自动生成的方法存根
		return uri;
	}

	@Override
	public String[] getRangesStrings() {
		// TODO 自动生成的方法存根
		return myApplication.getInstance().getEquipmentInfo().getDevicesRangStrings();
	}

	@Override
	public int getDevicesRange() {
		// TODO 自动生成的方法存根
		return myApplication.getInstance().getDevicesRange();
	}

	@Override
	public void setDeviesRange(int range) {
		// TODO 自动生成的方法存根
		if(myApplication.getInstance().getDevicesRange()!=range) {
			myApplication.getInstance().setDevicesRange(range);
			ScriptContent.getInstance().clearScriptName();
		}
	}

	@Override
	public String getVirtualDevices(int pos) {
		// TODO 自动生成的方法存根
		return myApplication.getInstance().getEquipmentInfo().getVirtualDevices(pos, RS232.getInstance());
	}

}
