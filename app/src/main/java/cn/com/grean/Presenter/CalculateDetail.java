package cn.com.grean.Presenter;

import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.model.DetailCalculate;
import cn.com.grean.model.DetailCalculateModel;
import cn.com.grean.monitoring.CalculateDetailFragmentView;

public class CalculateDetail implements DetailCalculatePresenter{
	private DetailCalculateModel model = new DetailCalculate();
	private CalculateDetailFragmentView view;
	
	public CalculateDetail(CalculateDetailFragmentView calculateDetailFragmentView) {
		// TODO 自动生成的构造函数存根
		view = calculateDetailFragmentView;
	}

	@Override
	public void showDetail() {
		// TODO 自动生成的方法存根
		model.loadCoefficient();
		if (view!=null) {
			String slope = tools.float2String4(model.getConsumerSlope());
			String intercept = tools.float2String4(model.getConsumerIntercept());
			String result = "当前结果:"+myApplication.getInstance().getEquipmentInfo().getResult(model.getNowValue());		
			String lowCalTimes = String.valueOf(model.getLowCalTimes());
			String highCalTimes = String.valueOf(model.getHighCalTimes());
			view.showInfo(slope,intercept,result, lowCalTimes, highCalTimes);
		}
	}

	@Override
	public boolean saveConsumerCoefficient(float slope, float intercept) {
		// TODO 自动生成的方法存根
		if (model.setConsumerCoefficient(slope, intercept)) {
			if (view!=null) {
				String text =  "当前结果:"+myApplication.getInstance().getEquipmentInfo().getResult(model.getNowValue());
				view.updataNowResult(text);
			}
			
			return true;
		}
		else {
			return false;
		}

	}

	@Override
	public boolean saveCalibrationTimes(int low, int high) {
		// TODO 自动生成的方法存根
		return model.saveCalTimes(low, high);
	}

}
