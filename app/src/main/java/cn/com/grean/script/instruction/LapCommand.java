package cn.com.grean.script.instruction;

import cn.com.grean.tools;
import cn.com.grean.script.LogListener;

public class LapCommand implements Command{
	private GhostCommand command;
	private GhostRecord record;
	private CommandSerialPort com;
	
	public LapCommand() {
		// TODO 自动生成的构造函数存根
		
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.record = ghostRecord;
		this.command = ghostCommand;
		this.com = com;
		if (params.length == 5) {
			byte [] data ={0x01,0x06,0x07,0x01,0x05,0x01,0x00,0x0a,0x00,0x64,0x0d,0x0a};
			data[3] = (byte) params[0];
			if (params[1]==0) {
				data[5] = 0x00;
			}
			else {
				data[5] = 0x01;
			}
			byte [] temp = tools.int2byte(params[2]);
			data[6] = temp[0];
			data[7] = temp[1];
			temp = tools.int2byte(params[3]*10);
			data[8] = temp[0];
			data[9] = temp[1];
			com.Send(data);
			if (params[4] <= 0) {
				return 1;
			}
			else {
				return params[4];
			}
		}
		else {
			return 1;
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
