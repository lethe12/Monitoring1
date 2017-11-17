package cn.com.grean;

import cn.com.grean.script.instruction.DevicesData;

/**
 * 定义可以使用的设备编号及名称
 * @author Administrator
 *
 */
public class EquipmentData {
	private boolean isMaintance = false,hasRobotArm=false,hasInjectionPump=false;
	private boolean [] pumpEnable = new boolean[4];//蠕动泵使能表
	private boolean [] valveEnable = new boolean[28];//电磁阀使能表
	private boolean [] valveState = new boolean[28];
	private boolean [] vdState = new boolean[5];
	private boolean [] pumpState = new boolean[4];
	private boolean [] vdEnable = new boolean[5];//虚拟设备使能表
	private String [] vdOnString = new String[5];
	private String [] vdOffString = new String[5];//虚拟设备关闭时文字
	private byte [] vdNumber = new byte[5];
	private boolean [] vdFinishAble = new boolean[5];
	
	public boolean isMaintance() {
		return isMaintance;
	}

	public void setMaintance(boolean isMaintance) {
		this.isMaintance = isMaintance;
	}

	public EquipmentData(boolean [] pump,boolean [] valve,boolean [] vd,String [] vdOn,String [] vdOff,byte [] num) {
		// TODO 自动生成的构造函数存根
		System.arraycopy(pump, 0, pumpEnable, 0, 4);
		System.arraycopy(valve, 0, valveEnable, 0, 28);
		System.arraycopy(vd, 0, vdEnable, 0, 5);
		System.arraycopy(vdOn, 0, vdOnString, 0, 5);
		System.arraycopy(vdOff, 0, vdOffString, 0, 5);
		System.arraycopy(num,0,vdNumber,0,5);
	}

	public void setVdFinishAble(boolean vdFinishAble[]){
        System.arraycopy(vdFinishAble,0,this.vdFinishAble,0,5);
    }

    public boolean getVdFinishAble(int i){
        if(i<5){
            return vdFinishAble[i];
        }else {
            return false;
        }
    }
	
	public String getVDOnString(int index) {
		if (index < 5) {
			return vdOnString[index];
		}
		return "VD"+String.valueOf(index)+"On";
	}
	
	public String getVDOffString(int index){
		if (index < 5) {
			return vdOffString[index];
		}
		else {
			return "VD"+String.valueOf(index)+"Off";
		}
	}
	
	public void setState(DevicesData data) {
		for (int i = 0; i < 5; i++) {
			vdState[i]=data.getVirtual(i);			
		}
		for (int i = 0; i < 25; i++) {
			valveState[i] = data.getValve(i);			
		}
		for (int i = 0; i < 4; i++) {
			pumpState[i]=data.getPump(i);			
		}
	}
	
	public boolean getValveState(int index){
		if (index < 28) {
			return valveEnable[index];
		}
		else {
			return false;
		}
	}
	
	public boolean getVDState(int index){
		if (index < 4) {
			return vdEnable[index];
		}
		else {
			return false;
		}
	}
	
	public boolean getPumpState(int index){
		if (index < 4) {
			return pumpEnable[index];
		}
		else {
			return false;
		}
	}
	
	public byte getVDNumber(int index){
		if (index < 4) {
			return vdNumber[index];
		}
		else {
			return 0;
		}
	}

	public boolean isHasRobotArm() {
		return hasRobotArm;
	}

	public void setHasRobotArm(boolean hasRobotArm) {
		this.hasRobotArm = hasRobotArm;
	}

	public boolean isHasInjectionPump() {
		return hasInjectionPump;
	}

	public void setHasInjectionPump(boolean hasInjectionPump) {
		this.hasInjectionPump = hasInjectionPump;
	}
}
