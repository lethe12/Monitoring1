package cn.com.grean.Presenter;

import android.util.Log;
import cn.com.grean.model.CalDetailInfo;
import cn.com.grean.model.DetailCal;
import cn.com.grean.model.DetailCalModel;
import cn.com.grean.model.DetailCalModel.CalInfoListener;
import cn.com.grean.model.DetailCalModel.NotifyAutoCalChanged;
import cn.com.grean.monitoring.CalibrationDetailView;

public class CalibrationDetail implements CalibrationDetailPresenter{
	private final static String tag = "CalibrationDetail";
	private CalibrationDetailView view;
	private NotifyAutoCalInfo notify=new NotifyAutoCalInfo();
	private DetailCalModel operate = new DetailCal(); 

	public CalibrationDetail(CalibrationDetailView calibrationDetailView) {
		// TODO 自动生成的构造函数存根
		this.view = calibrationDetailView;
	}

	@Override
	public void ShowInfo() {
		// TODO 自动生成的方法存根
		operate.loadDetailCal(new CalInfoListener() {
			
			@Override
			public void onComplete(CalDetailInfo info) {
				// TODO 自动生成的方法存根
				view.showDetail(info);
			}
		});
	}

	@Override
	public boolean calHigh(float data) {
		// TODO 自动生成的方法存根
		return operate.calHigh(data);
	}

	@Override
	public boolean calLow(float data) {
		// TODO 自动生成的方法存根
		Log.d(tag, "低点值"+String.valueOf(data));
		return operate.calLow(data);
	}

	@Override
	public boolean oneKeyToCal(float low, float high) {
		// TODO 自动生成的方法存根
		return operate.oneKeyToCal(low, high);
	}

	@Override
	public void SwitchAutoCalModel(boolean key) {
		// TODO 自动生成的方法存根
		operate.saveAutoCalSwitch(key, notify);

	}

	@Override
	public void SetNextCalDate(long date) {
		// TODO 自动生成的方法存根
		operate.saveAutoCalDate(date, notify);
	}

	@Override
	public void setAutoCalInterval(long interval) {
		// TODO 自动生成的方法存根
		operate.saveAutoCalInterval(interval, notify);
	}
	
	class NotifyAutoCalInfo implements NotifyAutoCalChanged{

		@Override
		public void onComplete(boolean enable, long date, long interval) {
			// TODO 自动生成的方法存根
			view.showAutoInfo(enable, date, interval);
			
		}
		
	}

}
