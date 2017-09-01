package cn.com.grean.script.instruction;

import cn.com.grean.tools;
import cn.com.grean.script.LogListener;


/**
 * 解析泵指令
 * @author Administrator
 *
 */
public class PumpCommand implements Command{
	private GhostCommand command;
	private GhostRecord record;
	private CommandSerialPort com;
	
	public PumpCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.record = ghostRecord;
		this.command = ghostCommand;
		this.com = com;
		if (params.length == 4) {
			byte [] data ={0x01,0x0d,0x06,0x01,0x01,0x00,0x00,0x00,0x00,0x0d,0x0a};
			data[3] = (byte) params[0];
			byte[] temp =new byte[2];
			if (params[1]<0) {
				temp = tools.int2byte(params[1]*-10);
				data[4] = 0x00;
			}
			else {				
				temp = tools.int2byte(params[1]*10);
			}
			data[5] = temp[0];
			data[6] = temp[1];
			temp = tools.int2byte(params[2]*10);
			data[7] = temp[0];
			data[8] = temp[1];
			com.Send(data);
			if (params[3] <= 0) {
				return 1;
			}
			else {
				return params[3];
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
