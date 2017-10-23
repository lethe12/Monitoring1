package cn.com.grean.script.instruction;

import cn.com.grean.myApplication;
import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;
import cn.com.grean.script.algorithm.Compute;

/**
 * CH,x,x 语句脚本
 * @author Administrator
 *
 */
public class CHxCommand implements Command{
	GeneralData data;
	public CHxCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
		data = (GeneralData) com.SyncSend(cmd);
		
		if(params.length ==2){
			if (params[1] == 1) {
				myApplication.getInstance().getCompute().setFirstValue(data.getOne(params[0]-1));
			}
			else if (params[1]==2) {
				String stage = ScriptContent.getInstance().getScriptName();
				Compute compute = myApplication.getInstance().getCompute();
				ResultListener resultListener = ScriptContent.getInstance();
				if (stage.equals("测量")) {				
					compute.Result(data.getOne(params[0]-1));
					resultListener.onResultComplete(compute);
				}
				else if (stage.equals("高点校准")) {
					compute.calibrationHigh(data.getOne(params[0]-1),0,1);
					resultListener.onCalibrationComplete(compute.getCalibrationHighLogInfo());
				}
				else if (stage.equals("低点校准")) {
					compute.calibrationLow(data.getOne(params[0]-1),0,1);
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
		
		return false;
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		change.onComplete(Command.NoChangeGhost);
	}

	

}
