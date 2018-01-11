package cn.com.grean.script.instruction;

import cn.com.grean.RS232;
import cn.com.grean.TitrationListener;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;
import cn.com.grean.script.algorithm.Compute;

public class TitrationCommand implements Command{

	public TitrationCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		if (params.length != 5) {
			return 1;
		}
		else {			
			byte [] data = {0x01,0x12,0x0b,0x02,0x00,(byte) 0xff,0x00,(byte) 0xff,0x01,0x00,0x00,0x00,0x00,0x00,0x0d,0x0a};
			data[3] = (byte) params[0];		
			byte[] temp = new byte[2];
			byte[] temp4=new byte[4];
			temp = tools.int2byte(params[1]*10);
			data[4] = temp[0];
			data[5] = temp[1];
			temp = tools.int2byte(params[2]*10);
			data[6] = temp[0];
			data[7] = temp[1];		
			temp4 = tools.float2byte(myApplication.getInstance().getCompute().getMediumValue());
			data[10] = temp4[3];
			data[11] = temp4[2];
			data[12] = temp4[1];
			data[13] = temp4[0];	
			data[8] = (byte) params[4];
			com.Send(data);
			if (params[3]<=0) {
				return 1;
			}
			else {			
				return params[3];
			}
		}
	}

	@Override
	public boolean RepetitiveExecute() {
		// TODO 自动生成的方法存根
		TitrationListener listener = RS232.getInstance();
		
		return listener.isCompleteTitration();
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		float value;
		TitrationListener listener = RS232.getInstance();
		if (listener.isCompleteTitration()) {
			value = listener.getTitrationTime();			
		}
		else {//下位机无响应
			value = 9999;
		}
		String stage = ScriptContent.getInstance().getScriptName();
		Compute compute = myApplication.getInstance().getCompute();
		ResultListener resultListener = ScriptContent.getInstance();
		if (stage.equals("测量")) {				
			compute.Result(value);
			resultListener.onResultComplete(compute);
		}
		else if (stage.equals("高点校准")) {
			compute.calibrationHigh(value,1,1);
			resultListener.onCalibrationComplete(compute.getCalibrationHighLogInfo());
		}
		else if (stage.equals("低点校准")) {
			compute.calibrationLow(value,1,1);
			resultListener.onCalibrationComplete(compute.getCalibrationLowLogInfo());
		}
		else {
			
		}
		
		change.onComplete(Command.NoChangeGhost);
	}

}
