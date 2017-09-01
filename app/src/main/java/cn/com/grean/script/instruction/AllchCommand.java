package cn.com.grean.script.instruction;

import cn.com.grean.myApplication;
import cn.com.grean.script.LogListener;

public class AllchCommand implements Command{

	public AllchCommand() {
		// TODO 自动生成的构造函数存根
	}


	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
		GeneralData data = (GeneralData) com.SyncSend(cmd);
		if (params.length == 1) {
			if (params[0] == 1) {
				myApplication.getInstance().getCompute().setFirstValue(data.getOne(0));
			}
			else if (params[0]==2) {
				myApplication.getInstance().getCompute().setSecondValue(data.getOne(0));
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
		change.onComplete(Command.NoChangeGhost);
	}

}
