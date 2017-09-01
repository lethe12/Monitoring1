package cn.com.grean.script.instruction;

import android.util.Log;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.script.LogListener;
import cn.com.grean.script.ResultListener;
import cn.com.grean.script.ScriptContent;
import cn.com.grean.script.algorithm.Compute;
import cn.com.grean.script.algorithm.DualWaveCompute;

/**
 * 双波长总氮检测方法
 * @author Administrator
 *
 */
public class TNAverageCommand implements Command{
	private final static String tag = "TNAverageCommand";
	private int ghostParams = 0;
	private int ghostFunction = 0;
	private GhostRecord record;
	private CommandSerialPort com;
	private LogListener logListener;
	
	private int times = 0;
	private float sum = 0f;
	private float sumRef = 0f;
	
	
	public TNAverageCommand() {
		// TODO 自动生成的构造函数存根
	}


	@Override
	public int execute(int[] params, CommandSerialPort com, GhostRecord ghostRecord, GhostCommand ghostCommand,LogListener logListener,String info) {
		// TODO 自动生成的方法存根
		this.record = ghostRecord;
		this.com = com;
		this.logListener = logListener;
		times = 0;
		sum = 0f;
		sumRef = 0f;
		if (params.length >= 3) {
			ghostParams = params[0]-1;
			ghostFunction = params[1];
			return params[2];
		}
		return 1;
	}

	@Override
	public boolean RepetitiveExecute() {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
		GeneralData data = (GeneralData) com.SyncSend(cmd);
		sum += data.getOne(ghostParams);
		sumRef += data.getOne(ghostParams+1);
		times++;
		
		if (record != null) {
			record.Record(data);
		}
		
		return false;
	}

	@Override
	public void end(ChangeRepetitiveCommand change) {
		// TODO 自动生成的方法存根
		float value = sum/times;
		float valueRef = sumRef / times;
		sum = 0f;
		sumRef = 0f;
		times = 0;
		Log.d(tag, "end");
		if (ghostFunction == 1) {
			logListener.writeLog("测量零点光强:"+tools.float2String0(value)+";参比零点光强:"+tools.float2String0(valueRef)+";");
			myApplication.getInstance().getDualCompute().setFirstValue(value,valueRef);
		}
		else if (ghostFunction == 2) {
			String stage = ScriptContent.getInstance().getScriptName();
			Compute compute = myApplication.getInstance().getCompute();
			DualWaveCompute dualCompute = myApplication.getInstance().getDualCompute();
			ResultListener resultListener = ScriptContent.getInstance();
			Log.d(tag, "end");
			if (stage.equals("测量")||stage.equals("测量标液")) {				
				dualCompute.Result(value,valueRef);
				resultListener.onResultComplete(compute);
			}
			else if (stage.equals("高点校准")) {
				ScriptContent.getInstance().addHighCalibrationIndex();
				int index = ScriptContent.getInstance().getHighCalibrationIndex();
				int times = ScriptContent.getInstance().getHighCalibrationTimes();
				logListener.writeLog("正在进行高点校准"+String.valueOf(index)+"/"+String.valueOf(times));
				dualCompute.calibrationHigh(value,valueRef,index,times);
				resultListener.onCalibrationComplete(compute.getCalibrationHighLogInfo());
			}
			else if (stage.equals("低点校准")) {
				ScriptContent.getInstance().addLowCalibrationIndex();
				int index = ScriptContent.getInstance().getLowCalibrationIndex();
				int times = ScriptContent.getInstance().getLowCalibrationTimes();
				logListener.writeLog("正在进行低点校准"+String.valueOf(index)+"/"+String.valueOf(times));
				dualCompute.calibrationLow(value,valueRef,index,times);
				resultListener.onCalibrationComplete(compute.getCalibrationLowLogInfo());
			}
			else {
				logListener.writeLog("脚本异常");
			}
		}
		change.onComplete(Command.NoChangeGhost);
	}

}
