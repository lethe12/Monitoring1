package cn.com.grean.script.instruction;

import cn.com.grean.EquipmentData;
import cn.com.grean.myApplication;

public class ScriptFinishCommandImp implements ScriptFinishCommand{
	@SuppressWarnings("unused")
	private final static String tag = "ScriptFinishCommandImp";
	private final static byte[] stopPump={0x01,0x06,0x02,0x01,0x04,0x0D,0x0A};
	private final static byte [] turnOffAll = {0x01,0x01,0x02,0x00,0x00,0x0d,0x0a};
	private boolean [] vdTrueTable = new boolean[5];
	private byte [] vdList = new byte  [5];
	
	public ScriptFinishCommandImp() {
		// TODO 自动生成的构造函数存根
		EquipmentData data = myApplication.getInstance().getEquipmentInfo().getEquipmentData();
		for (int i = 0; i < 5; i++) {
			if (data.getVDState(i)) {
				vdTrueTable[i] = true;
				vdList[i] = data.getVDNumber(i);
			}
			else {
				vdTrueTable[i]=false;
			}			
		}
	}

	@Override
	public void onComplete(CommandSerialPort com) {
		// TODO 自动生成的方法存根
		for (int i = 0; i < 5; i++) {
			if (vdTrueTable[i]) {
				byte [] data ={0x01,0x11,0x05,0x01,0x00,0x00,0x00,0x00,0x0d,0x0a};
				data[3] = vdList[i];				
				com.Send(data);
			}
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		com.Send(stopPump);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		com.Send(turnOffAll);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}

}
