package cn.com.grean.model;

import android.util.Log;
import cn.com.grean.EquipmentData;
import cn.com.grean.RS232;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.script.ScriptRun;
import cn.com.grean.script.instruction.DevicesData;
import cn.com.grean.script.instruction.GeneralData;
import cn.com.grean.script.plan.ScriptChain;

public class DetailMaintenance implements DetailMaintenanceModel{
	private final static String tag = "DetailMaintenance";

	public DetailMaintenance() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public String getParams() {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};		
		GeneralData data = (GeneralData) RS232.getInstance().SyncSend(cmd);
		if (data != null) {	
			
			return "CH1="+String.valueOf(data.getOne(0))+";CH2="+String.valueOf(data.getOne(1))+";"
			+"CH3="+String.valueOf(data.getOne(2))+";CH4="+String.valueOf(data.getOne(3))+";"
			+"液位1="+String.valueOf(data.getOne(4))+";\n液位2="+String.valueOf(data.getOne(5))+";"
			+"温度1="+String.valueOf(data.getOne(6))+";温度2="+String.valueOf(data.getOne(7))+";"
			+"CH9="+String.valueOf(data.getOne(8));
		}
		else {
			return null;
		}		
	}

	@Override
	public DevicesData getDevices() {
		// TODO 自动生成的方法存根
		byte [] cmd ={0x01,0x10,0x01,0x00,0x0D,0x0A};
		return (DevicesData) RS232.getInstance().SyncSend(cmd);
	}

	@Override
	public void setValveOn(int num) {
		// TODO 自动生成的方法存根
		if(num <=28){
			byte n=(byte) num;
			byte [] cmd ={0x01,0x01,0x02,0x01,0x01,0x0d,0x0a};
			cmd[3] = n;
			RS232.getInstance().Send(cmd);
		}
	}

	@Override
	public void setValveOff(int num) {
		// TODO 自动生成的方法存根
		if(num <=28){
			byte n=(byte) num;
			byte [] cmd ={0x01,0x01,0x02,0x01,0x00,0x0d,0x0a};
			cmd[3] = n;
			RS232.getInstance().Send(cmd);
		}
	}

	@Override
	public void calTemp(int ch, float data) {
		// TODO 自动生成的方法存根
		byte[] cmd={0x01,0x08,0x05,0x01,0x00,0x00,0x00,0x00,0x0D,0x0A};
		byte[] temp=new byte[4];
		cmd[3] = (byte) ch;
		temp = tools.float2byte(Float.valueOf(data));
		cmd[4]=temp[3];
		cmd[5]=temp[2];
		cmd[6]=temp[1];
		cmd[7]=temp[0];
		RS232.getInstance().Send(cmd);
	}

	@Override
	public void startPump(int num, int round, int time) {
		// TODO 自动生成的方法存根
		byte[] cmd={0x01,0x06,0x07,0x01,0x05,0x01,0x00,0x10,0x00,0x10,0x0D,0x0A};
		byte[] temp=new byte[2];		
		temp = tools.int2byte(Integer.valueOf(num));
		cmd[3] = temp[1];
		if(round < 0){
			cmd[5] = 0x00;
			temp = tools.int2byte(round*-1);
		}
		else {
			
			temp = tools.int2byte(round);
		}
		cmd[6] = temp[0];
		cmd[7] = temp[1];
		temp = tools.int2byte(time * 10);
		cmd[8] = temp[0];
		cmd[9] = temp[1];
		RS232.getInstance().Send(cmd);
	}

	@Override
	public void stopPump(int num) {
		// TODO 自动生成的方法存根
		byte[] cmd={0x01,0x06,0x02,0x01,0x04,0x0D,0x0A};
		cmd[3] = (byte) num;
		RS232.getInstance().Send(cmd);
	}

	@Override
	public void setPumpParams(int num, int params) {
		// TODO 自动生成的方法存根
		byte[] cmd={0x01,0x06,0x04,0x01,0x02,0x04,0x00,0x0D,0x0A};
		byte[] temp=new byte[2];
		cmd[3] = (byte) num;
		temp = tools.int2byte(params);
		cmd[5] = temp[0];
		cmd[6] = temp[1];
		RS232.getInstance().Send(cmd);
	}

	@Override
	public boolean initDevices() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("初始化");				
			run.addChainOfScript(chain);
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

	@Override
	public boolean clearDevices() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("清洗");				
			run.addChainOfScript(chain);
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

	@Override
	public void setMaintenanceModel(boolean key) {
		// TODO 自动生成的方法存根
		myApplication.getInstance().setMaintance(key);

	}

	@Override
	public EquipmentData loadEquipmentData() {
		// TODO 自动生成的方法存根
		EquipmentData data = myApplication.getInstance().getEquipmentInfo().getEquipmentData();
		data.setMaintance(myApplication.getInstance().isMaintenaning());
		return data;
	}

	@Override
	public void ctrlVD(int num, boolean key) {
		// TODO 自动生成的方法存根
		EquipmentData data = myApplication.getInstance().getEquipmentInfo().getEquipmentData();
		byte [] cmd = {0x01,0x11,0x05,0x01,0x01,0x00,0x00,0x00,0x0d,0x0a};
		cmd[3] = data.getVDNumber(num);
		if (key) {
			cmd[4] = 0x01;
		}
		else {
			cmd[4] = 0x00;
		}
		RS232.getInstance().Send(cmd);
	}

	

	

}
