package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;

public class CheckWarringCommand implements Command{

	public CheckWarringCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand, LogListener logListener, String info) {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
		GeneralData data = (GeneralData) com.SyncSend(cmd);
		if(params.length>3){
			if (params[1] == 0) {//小于
				if(data.getOne(params[0]-1)<params[2]){
					logListener.writeLog(info);
				}
			}
			else {
				if(data.getOne(params[0]-1)>params[2]){
					logListener.writeLog(info);
				}
			}
		}
		
		return 1;
	}

	@Override
	public boolean RepetitiveExecute() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		
	}

}
