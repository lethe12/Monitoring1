package cn.com.grean.Presenter;

import cn.com.grean.model.DetailWarning;
import cn.com.grean.model.DetailWarningModel;
import cn.com.grean.monitoring.WarningDetailFragmentView;

public class WarningDetail implements DetailWarningPresenter{
	private DetailWarningModel model = new DetailWarning();
	private WarningDetailFragmentView view;
	
	public WarningDetail(WarningDetailFragmentView warningDetailFragmentView) {
		// TODO 自动生成的构造函数存根
		view = warningDetailFragmentView;
	}

	@Override
	public void showWarningInfo() {
		// TODO 自动生成的方法存根
		if (view!=null) {			
			view.showWarningInfo(model.loadWarningInfo());
			view.showFloor(String.valueOf(model.getWarningFloor()));
			view.showUpper(String.valueOf(model.getWarningUpper()));
		}
	}

	@Override
	public void setWarningInfo(float floor, float upper) {
		// TODO 自动生成的方法存根
		model.saveWarningInfo(floor, upper);
	}

	@Override
	public void clearWarningInfo() {
		// TODO 自动生成的方法存根
		model.clearWarning();
		if (view!=null) {
			view.clearInfo();
		}
	}

	

	

}
