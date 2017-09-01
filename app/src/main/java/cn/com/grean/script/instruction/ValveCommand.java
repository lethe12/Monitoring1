package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;


public class ValveCommand implements Command{
	private GhostCommand command;
	private GhostRecord record;
	private CommandSerialPort com;
	
	public ValveCommand() {
		// TODO 自动生成的构造函数存根
	}


	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.record = ghostRecord;
		this.command = ghostCommand;
		this.com = com;
		if (params.length < 3) {
			return 1;
		}
		else {
			byte [] data = {0x01,0x01,0x02,0x01,0x00,0x0d,0x0a};
			data[3] = (byte) params[0];
			data[4] = (byte) params[1];
			com.Send(data);
			if (params[2]<=0) {
				return 1;
			}
			else {
				return params[2];
			}
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
