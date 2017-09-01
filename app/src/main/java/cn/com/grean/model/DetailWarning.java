package cn.com.grean.model;

import cn.com.grean.myApplication;
import cn.com.grean.script.WarningManager;

public class DetailWarning implements DetailWarningModel{
	private final static String tag = "DetailWarning";
	public DetailWarning() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void clearWarning() {
		// TODO 自动生成的方法存根
		myApplication app = myApplication.getInstance();
		WarningManager manager = WarningManager.getInstance();
		manager.clearWarringInfo(app);
		manager.clearErrorInfo(app);
	}

	@Override
	public String loadWarningInfo() {
		// TODO 自动生成的方法存根
		WarningManager manager = WarningManager.getInstance();
		return "错误:"+manager.getErrorString()+"\n警告:"+manager.getWarringString();
	}

	@Override
	public float getWarningFloor() {
		// TODO 自动生成的方法存根
		return WarningManager.getInstance().getFloor();
	}

	@Override
	public float getWarningUpper() {
		// TODO 自动生成的方法存根
		return WarningManager.getInstance().getUpper();
	}

	@Override
	public void saveWarningInfo(float floor, float upper) {
		// TODO 自动生成的方法存根
		WarningManager.getInstance().setRangeValue(floor, upper, myApplication.getInstance());
	}

	

}
