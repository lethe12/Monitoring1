package cn.com.grean.script.instruction;

import cn.com.grean.myApplication;
import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;
import cn.com.grean.script.algorithm.Compute;

public class MeanConmmand implements Command{
	private float [] databuff = new float [32]; 
	private GhostCommand command;
	private GhostRecord record;
	private CommandSerialPort com;

	public MeanConmmand() {
		// TODO 自动生成的构造函数存根
	}	

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.record = ghostRecord;
		this.command = ghostCommand;
		this.com = com;
		if (params.length == 3) {			
			byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
			GeneralData data = (GeneralData) com.SyncSend(cmd);
			databuff[params[1]-1] = data.getOne(params[0]-1);
			if (params[1]==params[2]) {//计算结果
				String stage = ScriptContent.getInstance().getScriptName();
				Compute compute = myApplication.getInstance().getCompute();
				ResultListener resultListener = ScriptContent.getInstance();
				float sum = 0f;
				for (int i = 0; i < databuff.length; i++) {
					sum += databuff[i];					
				}
				if (stage.equals("测量")) {				
					compute.Result(sum);
					resultListener.onResultComplete(compute);
				}
				else if (stage.equals("高点校准")) {
					compute.calibrationHigh(sum,1,1);
					resultListener.onCalibrationComplete(compute.getCalibrationHighLogInfo());
				}
				else if (stage.equals("低点校准")) {
					compute.calibrationLow(sum,1,1);
					resultListener.onCalibrationComplete(compute.getCalibrationLowLogInfo());
				}
				else {
					
				}
			}
		}
		
		return 1;
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
