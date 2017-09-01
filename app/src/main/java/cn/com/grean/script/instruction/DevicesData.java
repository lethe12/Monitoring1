package cn.com.grean.script.instruction;

public class DevicesData {
	private boolean [] pumpDevices = new boolean[16];
	private boolean [] valveDevices = new boolean[32];
	private boolean [] virtualDevices = new boolean [16];

	public DevicesData() {
		// TODO 自动生成的构造函数存根
	}
	
	public void setPump(byte h,byte l) {
		checkOneByteDevices(pumpDevices, 0, 8, l);
		checkOneByteDevices(pumpDevices, 8, 16, h);
	}
	
	public void setVirtual(byte h,byte l){
		checkOneByteDevices(virtualDevices, 0, 8, l);
		checkOneByteDevices(virtualDevices, 8, 16, h);
	}
	
	public void setValve(byte v4,byte v3,byte v2,byte v1) {
		checkOneByteDevices(valveDevices, 0, 8,v1);
		checkOneByteDevices(valveDevices, 8, 16,v2);
		checkOneByteDevices(valveDevices, 16, 24,v3);
		checkOneByteDevices(valveDevices, 24, 32,v4);
	}
	
	public boolean getPump(int index) {
		if (index < 16) {
			return pumpDevices[index];
		}
		else {
			return false;
		}
	}
	
	public boolean getValve(int index) {
		if (index <32) {
			return valveDevices[index];
		}
		else {
			return false;
		}
	}
	
	public boolean getVirtual(int index) {
		if (index < 16) {
			return virtualDevices[index];
		}
		else {
			return false;
		}
	}
	
	private void checkOneByteDevices(boolean[] device,int frome , int end,byte info){
		byte temp = 0x01;
		for (int i = frome; i < end; i++) {
			if ((temp & info)!=0x00) {				
				device[i] = true;
			}
			else {
				device[i] = false;
			}
			temp = (byte) (temp << 1);				
		}
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return "DevicesData";
	}

}
