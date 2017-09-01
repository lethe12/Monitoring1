package cn.com.grean.Presenter;

import cn.com.grean.model.DetailSystem;
import cn.com.grean.model.DetailSystemModel;
import cn.com.grean.monitoring.SystemFragmentView;

public class SystemDetail implements DetailSystemPresenter{
	private DetailSystemModel model = new DetailSystem();
	private SystemFragmentView view;
	
	public SystemDetail(SystemFragmentView systemFragmentView) {
		// TODO 自动生成的构造函数存根
		view = systemFragmentView;
	}

	@Override
	public String[] getBackLightTimeString() {
		// TODO 自动生成的方法存根
		return model.loadBackLightTimeString();
	}

	@Override
	public void showDitail() {
		// TODO 自动生成的方法存根
		
		if (view!=null) {
			view.showBackLightTime(model.getBackLightTime(),model.getNowDateString());
		}
	}

	@Override
	public void saveSystemTime(int year, int month, int day, int hour, int min) {
		// TODO 自动生成的方法存根
		model.setSystem(year, month, day, hour, min);
	}

	@Override
	public void showBackLightTime(int pos) {
		// TODO 自动生成的方法存根
		model.setBackLightTime(pos);
	}

}
