package cn.com.grean.model;

import android.util.Log;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.script.ScriptRun;
import cn.com.grean.script.plan.ScriptChain;

public class DetailOperate implements DetailOperateModel{

	private final static String tag = "DetailOperate";
	private long date;
	private long interval;
	private boolean autoEnable = false;
	
	public DetailOperate() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public synchronized boolean testStandard() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()||myApplication.getInstance().isMaintenaning()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("测量标液");				
			run.addChainOfScript(chain);
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}
	}

	@Override
	public synchronized boolean testOnce() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();
		if (run.isRun()||myApplication.getInstance().isMaintenaning()) {
			return false;
		}
		else {
			ScriptChain chain = new ScriptChain("测量");				
			run.addChainOfScript(chain);
			run.startSchedule(tools.nowtime2timestamp(), "None");			
			return true;
		}		
	}

	@Override
	public void saveAutoTestSwitch(boolean key,NotifyAutoTestChanged notify) {
		// TODO 自动生成的方法存根
		autoEnable = key;
		myApplication.getInstance().putConfig("AutoTestModel", key);
		if (autoEnable) {
			long now = tools.nowtime2timestamp();
			this.date = tools.calcNextTime(now, date, this.interval);
			myApplication.getInstance().putConfig("AutoTestDate", date);
		}
		if (notify!=null) {
			notify.onComplete(autoEnable, this.date, interval);
		}
	}

	@Override
	public void saveAutoTestInterval(long interval,NotifyAutoTestChanged notify) {
		// TODO 自动生成的方法存根
		this.interval = interval;
		myApplication.getInstance().putConfig("AutoTestInterval", interval);
		if (autoEnable) {
			long now = tools.nowtime2timestamp();
			this.date = tools.calcNextTime(now, date, this.interval);
			myApplication.getInstance().putConfig("AutoTestDate", date);
		}
		if (notify!=null) {
			notify.onComplete(autoEnable, this.date, interval);
		}
	}

	@Override
	public void saveNextTestDate(long plan,NotifyAutoTestChanged notify) {
		// TODO 自动生成的方法存根
		if (autoEnable) {
			long now = tools.nowtime2timestamp();
			this.date = tools.calcNextTime(now, plan, this.interval);
			myApplication.getInstance().putConfig("AutoTestDate", date);
			
		}
		if (notify!=null) {
			notify.onComplete(autoEnable, this.date,interval);
		}
	}

	@Override
	public void loadDetailMeasure(TestInfoListener reTestInfoListener) {
		// TODO 自动生成的方法存根
		myApplication app = myApplication.getInstance();
		String info = app.getConfigString("TestInfo");
		date = app.getConfigLong("AutoTestDate");
		interval = app.getConfigLong("AutoTestInterval");
		autoEnable = app.getConfigBoolean("AutoTestModel");
		Log.d(tag,"加载完成");
		
		TestDetailInfo testDetailInfo = new TestDetailInfo(info, date, interval, autoEnable);
		if(reTestInfoListener!= null){
			reTestInfoListener.onComplete(testDetailInfo);
		}
		
	}

	@Override
	public synchronized void testStop() {//通知测量
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();
		if(run.isRun()){
			run.stopScriptRun();
		}
	}



}
