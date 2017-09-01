package cn.com.grean.protocol;

import android.util.Log;
import cn.com.grean.tools;

public class ModBusByteProtocol implements GeneralBytesProtocol{
	@SuppressWarnings("unused")
	private final static String tag = "ModBusByteProtocol";
	private byte address = 0x01;
	private float result;
	private byte[] resultBuffer =new byte[4];

	public ModBusByteProtocol() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public byte[] ProcessProtocol(byte[] data, int len, ControlProtocol controlProtocol) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "start");
		if (data[0]!= address) {
			return null;
			
		}
		else {
			int crc = tools.calcCrc16(data, 0, len);
			if (crc == 0) {				
				byte[] cmd = new byte [len];
				int reg,value;
				reg = tools.byte2int(data, 2);
				value = tools.byte2int(data, 4);
				if(data[1]==0x06){//写寄存器					
					switch (reg) {
					case 0:
						switch (value) {
						case 0:
							controlProtocol.onComplete(ControlProtocol.STOP);
							break;
						case 1:
							controlProtocol.onComplete(ControlProtocol.TEST);
							break;
						case 2:
							controlProtocol.onComplete(ControlProtocol.CALIBRATION);
							break;
						case 3:
							controlProtocol.onComplete(ControlProtocol.QUALITY_CONTROL);
							break;
						case 4:
							controlProtocol.onComplete(ControlProtocol.INIT);
							break;

						default:
							break;
						}
						
						break;
						
					default:
						break;
					}
					System.arraycopy(data, 0, cmd, 0, len);
					return cmd;
				}
				else if ((data[1]==0x03)||(data[2]==0x04)) {//查数据
					Log.d(tag, "看数据"+String.valueOf(reg)+";"+String.valueOf(value));
					byte[] returnCMD = new byte [value*2+5]; 
					returnCMD[0] = data[0];
					returnCMD[1] = data[1];
					returnCMD[2] = (byte) (value * 2);
					setRegister(reg, value, returnCMD, 3);
					int crcBack = tools.calcCrc16(returnCMD, 0, value*2+3);
					byte [] crcBuff = tools.int2byte(crcBack);
					returnCMD[value*2+3] = crcBuff[0];
					returnCMD[value*2+4] = crcBuff[1];
					return returnCMD;
				}
				else {
					return null;
				}
				
			}
			else {
				return null;
			}
			
			
		}
		
	}

	@Override
	public void setModBusAddress(byte address) {
		// TODO 自动生成的方法存根
		this.address = address;
	}
	
	public byte [] getStartFrame(){
		byte [] cmd = {0x01,0x06,0x00,0x00,0x00,0x01,0x00,0x00};
		cmd[0] = address;
		int temp = tools.calcCrc16(cmd, 0, 6);
		byte [] crc = tools.int2byte(temp);
		cmd[6] = crc[0];
		cmd[7] = crc[1];
		return cmd;
	}
	
	public byte [] getInquireFrame(){
		byte [] cmd = {0x01,0x03,0x00,0x01,0x00,0x02,0x00,0x00};
		cmd[0] = address;
		int temp = tools.calcCrc16(cmd, 0, 6);
		byte [] crc = tools.int2byte(temp);
		cmd[6] = crc[0];
		cmd[7] = crc[1];
		return cmd;
	}
	
	public byte [] getReturnFrame(){
		byte [] data = tools.float2byte(result);
		byte [] cmd = {0x01,0x03,0x04,0x00,0x00,0x00,0x01,0x00,0x00};
		cmd[0] = address;
		cmd[3] = data[3];
		cmd[4] = data[2];
		cmd[5] = data[1];
		cmd[6] = data[0];
		int temp = tools.calcCrc16(cmd, 0, 7);
		byte [] crc = tools.int2byte(temp);
		cmd[7] = crc[0];
		cmd[8] = crc[1];
		return cmd;
	}

	@Override
	public void setNowResult(float result) {
		// TODO 自动生成的方法存根
		this.result = result;
		resultBuffer = tools.float2byte(result);
	}
	/**
	 * 写ModBus寄存器
	 * @param start 起始寄存器位置
	 * @param len 写入寄存器长度
	 * @param buffer 被写入字节流
	 * @param index 指针
	 */
	private void setRegister(int start, int len, byte[] buffer, int index) {
		// TODO 自动生成的方法存根
		if (buffer.length == (index+len*2+2)) {//确保数组够长
			int bufferIndex=index,reg;
			
			for (int i = 0; i < len; i++) {
				reg = start+i;
				switch (reg) {
				case 1:
					buffer[bufferIndex++]=resultBuffer[3];
					buffer[bufferIndex++]=resultBuffer[2];
					break;
				case 2:
					buffer[bufferIndex++]=resultBuffer[1];
					buffer[bufferIndex++]=resultBuffer[0];
					break;

				default:
					buffer[bufferIndex++]=0x00;
					buffer[bufferIndex++]=0x00;
					break;
				}
				
			}
		}
	}

	@Override
	public float getNowResult() {
		// TODO 自动生成的方法存根
		return result;
	}

}
