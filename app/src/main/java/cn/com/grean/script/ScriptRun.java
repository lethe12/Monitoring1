package cn.com.grean.script;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Observer;

import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.monitoring.MeasureView;
import cn.com.grean.script.ScriptInfo.ScriptCompleteListener;
import cn.com.grean.script.plan.AutoCalPlan;
import cn.com.grean.script.plan.AutoTestPlan;
import cn.com.grean.script.plan.ChainOfScript;
import cn.com.grean.script.plan.NoneTestPlan;
import cn.com.grean.script.plan.ScheduleListener;
import cn.com.grean.script.plan.ScheduleOfScriptRun;
import android.util.Log;

/**
 * 控制当前脚本运行的类，单例化，同一时间只能运行一个脚本,脚本类的Prsenter层,其他模块统一调用这个类来操作脚本
 * @author Administrator
 *
 */
public class ScriptRun implements ScriptRunListener{
	private final static String tag = "ScriptRun";
	public final static int ScriptCommandInfo = 1;
	public final static int ScriptResultInfo = 2;
	private boolean run = false;
	//private Context context = null;
	ScriptContent scriptContent;
	private Observer logObserver;
	private MeasureView measureView;
	private static ScriptRun instance = new ScriptRun();
	private LinkedList<ChainOfScript> poolChainOfScripts = new LinkedList<ChainOfScript>();
	private Hashtable<String, ScheduleOfScriptRun> endOfScriptPool = new Hashtable<String, ScheduleOfScriptRun>();
	private ScheduleOfScriptRun currentEnd;
	private ScheduleListener scheduleListener;
	private long lastSchedule;

	public void setScheduleListener(ScheduleListener scheduleListener) {
		this.scheduleListener = scheduleListener;
	}

	private ScheduleOfScriptRun getScheduleOfScriptRun (String name){
		if (name.equals("AUTOTest")) {
			return new AutoTestPlan();
		}
		else if (name.equals("AUTOCalibration")) {
			return new AutoCalPlan();
		}
		else {
			return new NoneTestPlan();
		}
	}
	
	private synchronized ScheduleOfScriptRun getCurrentSchedule(String name){
		ScheduleOfScriptRun schedule = endOfScriptPool.get(name);
		if (schedule == null) {
			schedule = getScheduleOfScriptRun(name);
			endOfScriptPool.put(name, schedule);
		}
		return schedule;
	}
	/**
	 * 判断当前任务链中的结束任务是否为 end ，如是则取消当前结束任务，如不是则保持现有不变
	 * @param end 需要与当前任务比较的结束任务名称
	 */
	public void cancelCurrentEnd(String end){
		if (currentEnd != null) {			
			if (currentEnd.equals(getCurrentSchedule(end))) {
				Log.d(tag, "same end");
				currentEnd = getCurrentSchedule("None");
			}
			else {
				Log.d(tag, "different end");
			}
		}
	}
	/**
	 * 开始一项计划任务
	 * @param firstDate 当前时间戳
	 * @param end 结束该计划任务后执行的任务
	 */
	public void startSchedule(long firstDate,String end) {
		Log.d(tag, "startSchedule");

		this.currentEnd = getCurrentSchedule(end);
		lastSchedule = firstDate;

		if(!poolChainOfScripts.isEmpty()){
			//第一次清空校准次数
			scriptContent.initCalibrationTimes();
			ChainOfScript chain = poolChainOfScripts.poll();
			if (!startScript(chain.getScriptName(),firstDate)) {
				if (scheduleListener != null) {					
					if (end.equals("AUTOTest")) {					
						this.scheduleListener.isBusy(ScheduleListener.AUTOTest);
					}
					else if (end.equals("AUTOCalibration")) {					
						this.scheduleListener.isBusy(ScheduleListener.AUTOCalibration);
					}
					else{
						this.scheduleListener.isBusy(ScheduleListener.CancelAll);
					}
				}				
			}
		}
		else {

			currentEnd.timing(scheduleListener,currentEnd.getNexDate(lastSchedule));
		}
	}
	
	private void nextSchedule(){
		if(!poolChainOfScripts.isEmpty()){
			ChainOfScript chain = poolChainOfScripts.poll();
			startScript(chain.getScriptName(),tools.nowtime2timestamp());
		}
		else {	
			currentEnd.timing(scheduleListener,currentEnd.getNexDate(lastSchedule));
			//run = false;
		}
	}
	
	public synchronized void addChainOfScript(ChainOfScript chainOfScript){
		
		poolChainOfScripts.add(chainOfScript);
		
	}
	
	
	
	public boolean isRun() {
		return run;
	}

	public static ScriptRun getInstance() {
		return instance;
	}

	private ScriptRun() {
		// TODO 自动生成的构造函数存根
		scriptContent=ScriptContent.getInstance();

	}
	
	private synchronized boolean startScript(String name,long testTimestamp) {
		if (run) {
			Log.d(tag, "正忙");
			return false;
		}
		else {			
			run = true;
			//Log.d(tag, "载入日志");
			scriptContent.deleteObservers();
			logObserver = myApplication.getInstance();
			scriptContent.addObserver(logObserver);
			scriptContent.upLoadScript(name, new ScriptCompleteListener() {
				//脚本结束，回调方法
				public void onComplete() {
					// TODO 自动生成的方法存根
					measureView.showCommandinfo("待机", 0, 100);//清空进度条和显示信息
					run = false;
					Log.d(tag, "脚本结束");
					nextSchedule();

				}
			},testTimestamp);
			return true;
		}
	}
	
	public void stopScriptRun(){
		//run=false;
		scriptContent.run = false;
		if (!poolChainOfScripts.isEmpty()) {
			poolChainOfScripts.clear();
		}
	}

	public void setMeasureView(MeasureView measureview){
		this.measureView = measureview;
	}

	@Override
	public void updataCommandInfo(String info, int Progress, int max) {
		// TODO 自动生成的方法存根
		if (measureView != null) {
			if (info != null) {
				Log.d(tag, info);
			}
			measureView.showCommandinfo(info, Progress, max);
		}
	}

	@Override
	public void updataResultInfo(String result, String testTime) {
		// TODO 自动生成的方法存根
		measureView.showResultInfo(result, testTime);
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return tag;
	}

}
