package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;


public class NonCommand implements Command{
	//private Object data;

	public NonCommand() {
		// TODO 自动生成的构造函数存根
	}


	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		return 0;
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
