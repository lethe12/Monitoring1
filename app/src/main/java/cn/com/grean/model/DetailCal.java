package cn.com.grean.model;

import android.util.Log;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.script.ScriptRun;
import cn.com.grean.script.plan.ScriptChain;

public class DetailCal implements DetailCalModel{
	private final static String tag = "DetailCal";
	private long autoCalDate;
	private long autoCalInterval;
	private boolean autoCalEnable = false;

	public DetailCal() {
		// TODO 自动生成的构造函数存根
	}



	@Override
	public void saveAutoCalSwitch(boolean key, NotifyAutoCalChanged notify) {
		// TODO 自动生成的方法存根
		autoCalEnable = key;
		myApplication.getInstance().putConfig("AutoCalModel", key);
		if (autoCalEnable) {
			long now = tools.nowtime2timestamp();
			this.autoCalDate = tools.calcNextTime(now, autoCalDate, this.autoCalInterval);
			myApplication.getInstance().putConfig("AutoCalDate", autoCalDate);
		}
		if (notify!=null) {
			notify.onComplete(autoCalEnable, this.autoCalDate, autoCalInterval);
		}
	}

	@Override
	public void saveAutoCalDate(long plan, NotifyAutoCalChanged notify) {
		// TODO 自动生成的方法存根
		if (autoCalEnable) {
			long now = tools.nowtime2timestamp();
			autoCalDate = tools.calcNextTime(now, plan, this.autoCalInterval);
			myApplication.getInstance().putConfig("AutoCalDate", autoCalDate);
			
		}
		if (notify!=null) {
			notify.onComplete(autoCalEnable, autoCalDate,autoCalInterval);
		}
	}

	@Override
	public void saveAutoCalInterval(long interval, NotifyAutoCalChanged notify) {
		// TODO 自动生成的方法存根
		this.autoCalInterval = interval;
		myApplication.getInstance().putConfig("AutoCalInterval", interval);
		if (autoCalEnable) {
			long now = tools.nowtime2timestamp();
			this.autoCalDate = tools.calcNextTime(now, autoCalDate, this.autoCalInterval);
			myApplication.getInstance().putConfig("AutoCalDate", autoCalDate);
		}
		if (notify!=null) {
			notify.onComplete(autoCalEnable, this.autoCalDate, autoCalInterval);
		}
	}

	@Override
	public void loadDetailCal(CalInfoListener listener) {
		// TODO 自动生成的方法存根
		myApplication app = myApplication.getInstance();
		String info = app.getCompute().getCalibrationInfo();
		autoCalDate = app.getConfigLong("AutoCalDate");
		autoCalInterval = app.getConfigLong("AutoCalInterval");
		autoCalEnable = app.getConfigBoolean("AutoCalModel");
		Log.d(tag,"加载完成");
		
		CalDetailInfo calDetailInfo = new CalDetailInfo(info, autoCalDate, autoCalInterval, autoCalEnable, app.getConfigFloat("lastTargetLow"),app.getConfigFloat("lastTargetHigh"));
		if(listener!= null){
			listener.onComplete(calDetailInfo);
		}
	}

	@Override
	public synchronized boolean calHigh(float data) {
		// TODO 自动生成的方法存根
		myApplication app =myApplication.getInstance();
		app.getCompute().setHighPoint(data);
		int times = app.getConfigInt("HighCalibrationTimes");
		if (times < 1) {
			times = 1;
		}
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()||myApplication.getInstance().isMaintenaning()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("高点校准");		
			for (int i = 0; i < times; i++) {
				run.addChainOfScript(chain);
			}
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

	@Override
	public synchronized boolean calLow(float data) {
		// TODO 自动生成的方法存根
		myApplication app =myApplication.getInstance();
		app.getCompute().setLowPoint(data);
		int times = app.getConfigInt("LowCalibrationTimes");
		if (times < 1) {
			times = 1;
		}
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()||myApplication.getInstance().isMaintenaning()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("低点校准");	
			for (int i = 0; i < times; i++) {				
				run.addChainOfScript(chain);
			}
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

	@Override
	public synchronized boolean oneKeyToCal(float low, float high) {
		// TODO 自动生成的方法存根
		myApplication app =myApplication.getInstance();
		app.getCompute().setLowPoint(low);
		app.getCompute().setHighPoint(high);
		int lowTimes =  app.getConfigInt("LowCalibrationTimes");
		if(lowTimes<1){
			lowTimes = 1;
		}
		int highTimes = app.getConfigInt("HighCalibrationTimes");
		if (highTimes < 1) {
			highTimes = 1;
			
		}
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()||myApplication.getInstance().isMaintenaning()) {
			return false;
		}
		else {
			ScriptChain step1 = new ScriptChain("低点校准");	
			ScriptChain step2 = new ScriptChain("高点校准");
			for (int i = 0; i < lowTimes; i++) {
				run.addChainOfScript(step1);				
			}
			for (int i = 0; i < highTimes; i++) {
				run.addChainOfScript(step2);
			}
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

}
