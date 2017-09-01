package cn.com.grean.script;

import java.util.LinkedList;
import java.util.Observable;

import android.util.Log;

import cn.com.grean.RS232;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.protocol.ProtocolProcessorImp;
import cn.com.grean.script.algorithm.Compute;
import cn.com.grean.script.instruction.ChangeRepetitiveCommand;
import cn.com.grean.script.instruction.Command;
import cn.com.grean.script.instruction.CommandFactory;
import cn.com.grean.script.instruction.CommandSerialPort;
import cn.com.grean.script.instruction.GeneralData;
import cn.com.grean.script.instruction.GhostCommand;
import cn.com.grean.script.instruction.GhostRecord;
import cn.com.grean.script.instruction.ScriptFinishCommand;
import cn.com.grean.script.instruction.ScriptFinishCommandImp;

/**
 * 脚本内容执行 可被LogObserver 和 DataObserver 记录和观察
 * @author Administrator
 *
 */
public class ScriptContent extends Observable implements ScriptInfo,ResultListener{
	private final static String tag = "ScriptContent";
	private String scriptName="";
	private LinkedList<String> content = new LinkedList<String>();
	public boolean run = false;
	private int stepNow;
	private int stepSum;
	private CommandSerialPort com;
	private ScriptCompleteListener listener;
	private ScriptThread scriptThread; 
	private CommandFactory commandFactory;
	private Command currentCMD;
	private GhostCommand ghostCommand;
	private ScriptFinishCommand finishCommand;
	private ScriptRunListener scriptRunListener;
	private GhostRecord ghostRecord;
	private long timestamp = 0l;
	
	private int lowCalibrationTimes = 1;
	private int lowCalibrationIndex= 0 ;
	private int highCalibrationTimes = 1;
	private int highCalibrationIndex = 0;
	
	public int getLowCalibrationTimes() {
		return lowCalibrationTimes;
	}
	
	public int getLowCalibrationIndex() {
		return lowCalibrationIndex;
	}
	
	public int getHighCalibrationTimes() {
		return highCalibrationTimes;
	}
	
	public int getHighCalibrationIndex() {
		return highCalibrationIndex;
	}
	
	public void addLowCalibrationIndex(){
		if (lowCalibrationIndex < lowCalibrationTimes) {
			lowCalibrationIndex++;
		}
		else {
			lowCalibrationIndex = lowCalibrationTimes;
		}
	}
	
	public void addHighCalibrationIndex(){
		if (highCalibrationIndex < highCalibrationTimes) {
			highCalibrationIndex++;
		}
		else {
			highCalibrationIndex = highCalibrationTimes;
		}
	}
	
	public void initCalibrationTimes(){
		lowCalibrationTimes = myApplication.getInstance().getConfigInt("LowCalibrationTimes");
		highCalibrationTimes = myApplication.getInstance().getConfigInt("HighCalibrationTimes");
		if (lowCalibrationTimes<1) {
			lowCalibrationTimes = 1;
		}
		if (highCalibrationTimes < 1) {
			highCalibrationTimes = 1;
		}
		lowCalibrationIndex = 0;
		highCalibrationIndex = 0;
	}
	
	
	public int getStepNow() {
		return stepNow;
	}
	public int getStepSum() {
		return stepSum;
	}
	public String getScriptName() {
		return scriptName;
	}
	
	public void clearScriptName(){
		scriptName = "";
	}

	private static ScriptContent instance = new ScriptContent(); 
	public static ScriptContent getInstance() {
		return instance;
	}
	private ScriptContent() {
		// TODO 自动生成的构造函数存根
		com=RS232.getInstance();
		ghostRecord = new ScriptGhostRecord();
		
	}
	
	public void upLoadScript(String name,ScriptCompleteListener listener,long timesamp){
		Log.d(tag, name);
		this.timestamp = timesamp;
		if (scriptName != name) {			
			scriptName = name;
			ScriptReader reader = myApplication.getInstance();
			reader.loadScriptContent(name, content);
			/*for (int i = 0; i < content.size(); i++) {
				Log.d(tag,content.get(i));
				
			}*/
			/*content.clear();
			
			
			content.add("Starttest,87");
			content.add("Note,开始脚本");
			content.add("TP,ALLCH,1");
			content.add("TP,READ,1,0");
			content.add("CH1,1,0");
			content.add("Pump,1,10,10,11");
			content.add("Valve,7,0,0");
			content.add("Sleep,12");
			content.add("Pump,1,10,10,11");
			content.add("Valve,7,0,0");
			content.add("Sleep,12");
			content.add("Pump,1,10,10,11");
			content.add("Valve,7,0,0");
			content.add("Sleep,12");
			content.add("TP,Endtest");*/
		}		
		stepNow = 0;
		stepSum = content.size();
		run = true;
		scriptRunListener = ScriptRun.getInstance();
		scriptThread = new ScriptThread();
		scriptThread.start();
		this.listener = listener;
		setChanged();
		notifyObservers(new LogFormat("开始执行"+scriptName));
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return tag;
	}
	
	class ScriptThread extends Thread implements LogListener,ChangeRepetitiveCommand{
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			super.run();
			int [] params;
			int miniStep;
			String info;
			
			commandFactory = CommandFactory.getInstance();
			ghostCommand = commandFactory.getGhostCommand("None");
			//ghostRecord = 
			while ((run)&&(stepNow < stepSum)) {
				//Log.d(tag, content.get(stepNow));
				commandFactory.translateCommand(content.get(stepNow++));				
				currentCMD = commandFactory.getCommandContent();
				params = commandFactory.getParams();
				info = commandFactory.getContent();
				miniStep = currentCMD.execute(params, com,ghostRecord,ghostCommand,this,info);
				if (scriptRunListener != null) {
					scriptRunListener.updataCommandInfo(info,stepNow,stepSum);
				}
				
				for (int i = 0; i < miniStep; i++) {					
					try {
						Thread.sleep(500);
						if(currentCMD.RepetitiveExecute()){
							break;
						}
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					if (!run) {
						break;
					}
					
				}
				
				currentCMD.end(this);
			}
			run = false;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if (finishCommand == null ) {
				finishCommand = new ScriptFinishCommandImp();
			}
			finishCommand.onComplete(com);
			
			setGhostCommand(Command.DisableChangeGhost, commandFactory);
			
			setChanged();
			notifyObservers(new LogFormat("结束流程"));
			if (listener != null) {				
				listener.onComplete();
			}
		}

		@Override
		public void writeLog(String info) {
			// TODO 自动生成的方法存根
			if (info!=null) {
				setChanged();
				notifyObservers(new LogFormat(info));
			}
		}

		@Override
		public void onComplete(int cmd) {
			// TODO 自动生成的方法存根
			setGhostCommand(cmd, commandFactory);
		}
	}
	
	class ScriptGhostRecord implements GhostRecord{
		private ScriptGhostListener scriptGhostListener;
		public ScriptGhostRecord(){
			scriptGhostListener = myApplication.getInstance();
		}

		@Override
		public void Record(GeneralData data) {
			// TODO 自动生成的方法存根
			if (scriptGhostListener != null) {
				scriptGhostListener.RecordGhost(data);
			}
		}
		
	}
	
	private void setGhostCommand(int key,CommandFactory cmdFactory){
		switch (key) {
		case Command.DisableChangeGhost:
			ghostCommand = cmdFactory.getGhostCommand("None");
			break;
		case Command.EnableChangeGhost:
			ghostCommand = cmdFactory.getGhostCommand("General");
			break;
		case Command.NoChangeGhost:
			
			break;
		default:
			break;
		}
	}
	@Override
	public void onResultComplete(Compute compute) {
		// TODO 自动生成的方法存根
		String result = myApplication.getInstance().getEquipmentInfo().getResult(compute.getComputeResult());
		String testTime = tools.timestamp2string(timestamp);
		scriptRunListener.updataResultInfo(result, testTime);
		setChanged();
		notifyObservers(new ResultDataFormat(compute, timestamp));
		setChanged();
		notifyObservers(new LogFormat(compute.getTestLogInfo()));
		ProtocolProcessorImp.getInstance().setProtocolResult(compute.getComputeResult());
		ProtocolProcessorImp.getInstance().setProtocolTimeStamp(timestamp);
	}
	@Override
	public void onCalibrationComplete(String info) {
		// TODO 自动生成的方法存根
		setChanged();
		notifyObservers(new LogFormat(info));
	}
	@Override
	public void onErrorComplete(String info) {
		// TODO 自动生成的方法存根
		setChanged();
		notifyObservers(new ErrorFormat(info));
		//测量界面标记
	}

}
