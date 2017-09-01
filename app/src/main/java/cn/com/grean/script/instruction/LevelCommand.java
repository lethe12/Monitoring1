package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;

public class LevelCommand implements Command{
	private int ghostParams = 0;
	//private int ghostFunction = 0;
	
	private final static byte [] cmd ={0x01,0x07,0x01,0x00,0x0D,0x0A};
	private GeneralData generalData;
	private boolean hasWater = false;
	CommandSerialPort com;

	public LevelCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.com = com;
		if (params.length >= 2) {
			if (params[0] == 1) {
				ghostParams = 5;
			}
			else {
				ghostParams = 8;
			}
			return params[1];
		}
		return 0;
	}

	@Override
	public boolean RepetitiveExecute() {
		// TODO 自动生成的方法存根
		generalData = (GeneralData) com.SyncSend(cmd);
		if (generalData.getOne(ghostParams)!=0f) {
			hasWater = true;
		}
		
		return false;
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		if (!hasWater) {//没水
			ResultListener resultListener = ScriptContent.getInstance();
			resultListener.onErrorComplete("未检测到样品,终止流程");
			ScriptContent.getInstance().run=false;
			
		}
		hasWater = false;
		change.onComplete(Command.NoChangeGhost);
	}

}
