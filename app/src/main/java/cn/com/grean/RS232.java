package cn.com.grean;

import cn.com.grean.script.instruction.CommandSerialPort;
import cn.com.grean.script.instruction.DevicesData;
import cn.com.grean.script.instruction.GeneralData;
import android.util.Log;

public class RS232 extends SerialPortCommunication implements CommandSerialPort,TitrationListener{
	private final static String tag = "RS232";
	private Object syncData = null;
	private static RS232 instance = new RS232();
	private GeneralData generalData;
	private DevicesData devicesData;
	private boolean isCompleteTitration = false;
	private float titrationTime = 0f;

	public static RS232 getInstance() {
		return instance;
	}

	private RS232() {
		// TODO 自动生成的构造函数存根
		super(0, 8, 9600, 1, 'n');//默认串口0 9600 8  无  1
		generalData = new GeneralData();
		devicesData = new DevicesData();
	}

	@Override
	protected Boolean handleBuffer() {
		// TODO 自动生成的方法存根
		if (size >= 6) {
			if ((mybuf[0] == 0x01)&&(mybuf[size-2]==0x0D)&&(mybuf[size-1]==0x0A)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	//发送异步串口信息
	public void Send(byte [] data){
		sendBuff.add(data);
	}
	
	public Object SyncSend(byte [] data){
		syncData = null;
		int times=0;
		Log.d(tag,"发送");
		sendBuff.add(data);
		while ((times <10)&&(syncData == null)) {
			try {
				times++;
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		//String string = (String) syncData;

		return syncData;
	}

	@Override
	protected void CommunicationProtocal(byte[] rec, int len) {
		// TODO 自动生成的方法存根
		if (CheckRS232(rec, len)) {			
			switch (rec[1]) {
			case 0x07:
				generalData.setData(rec, len);
				syncData = generalData;
				break;
			case 0x10:
				devicesData.setPump(rec[3], rec[4]);
				devicesData.setValve(rec[5], rec[6], rec[7], rec[8]);
				devicesData.setVirtual(rec[9], rec[10]);
				syncData = devicesData;
				break;
				
			default:
				syncData = null;
				break;
			}
		}
	}

	@Override
	protected void AsynchronousCommunicationProtocal(byte[] rec, int len) {
		// TODO 自动生成的方法存根
		/*byte[] cmd ={'G','R','E','A','N'};
		Send(cmd);*/
		if (CheckRS232(rec, len)) {
			switch (rec[1]) {
			case 0x12:
				int temp =tools.byte2int(rec, 4);
				titrationTime = (float)temp/10f;
				isCompleteTitration = true;
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 校验通讯协议
	 * @param rec
	 * @param len
	 * @return
	 */
	 private Boolean CheckRS232(byte[] rec,int len){
		if (rec[0]!=0x01) {
			return false;
		}
		 
		if ((len-5)!=(rec[2])) {
			return false;
		}
		
		if (rec[len-1]!=0x0A) {
			return false;
		} 
		
		if ((rec[len-2])!=0x0D) {
			return false;
		}
		//Log.d("RS232", "校验成功");
		return true;
	 }

	@Override
	public void startTitration() {
		// TODO 自动生成的方法存根
		isCompleteTitration = false;
	}

	@Override
	public boolean isCompleteTitration() {
		// TODO 自动生成的方法存根
		return isCompleteTitration;
	}

	@Override
	public float getTitrationTime() {
		// TODO 自动生成的方法存根
		return titrationTime;
	}

}
