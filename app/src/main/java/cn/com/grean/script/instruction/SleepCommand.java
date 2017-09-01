package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;


public class SleepCommand implements Command{
	private CommandSerialPort com;
	private GhostRecord record;
	private GhostCommand command;
	public SleepCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.com = com;
		this.record = ghostRecord;
		this.command = ghostCommand;
		if (params.length <1) {
			return 1;
		}
		else {			
			return params[0];
		}
	}

	@Override
	public boolean RepetitiveExecute() {
		// TODO 自动生成的方法存根
		if (command!=null) {
			command.execute(record, com);
		}
		return false;
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		change.onComplete(Command.NoChangeGhost);
	}

	

}
