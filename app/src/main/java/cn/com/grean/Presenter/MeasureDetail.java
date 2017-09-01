package cn.com.grean.Presenter;

import cn.com.grean.model.DetailOperate;
import cn.com.grean.model.DetailOperateModel;
import cn.com.grean.model.DetailOperateModel.NotifyAutoTestChanged;
import cn.com.grean.model.DetailOperateModel.TestInfoListener;
import cn.com.grean.model.TestDetailInfo;
import cn.com.grean.monitoring.MeasureDetailView;

public class MeasureDetail implements MeasureDetailPresenter{
	private MeasureDetailView view;
	private DetailOperateModel operate = new DetailOperate();
	private NotifyDetailAutoTestInfo notify = new NotifyDetailAutoTestInfo();

	public MeasureDetail(MeasureDetailView measureDetailView) {
		// TODO 自动生成的构造函数存根
		this.view = measureDetailView;
	}

	@Override
	public void ShowInfo() {
		// TODO 自动生成的方法存根
		operate.loadDetailMeasure(new TestInfoListener() {
			
			@Override
			public void onComplete(TestDetailInfo testDetailInfo) {
				// TODO 自动生成的方法存根
				view.showDetail(testDetailInfo);
			}
		});
	}

	@Override
	public boolean TestOnce() {
		// TODO 自动生成的方法存根
		
		return operate.testOnce();
	}

	@Override
	public boolean TestStandard() {
		// TODO 自动生成的方法存根
		return operate.testStandard();
	}

	@Override
	public void StopTest() {
		// TODO 自动生成的方法存根
		operate.testStop();
	}

	@Override
	public void SwitchAutoTestModel(boolean key) {
		// TODO 自动生成的方法存根
		operate.saveAutoTestSwitch(key, notify);
	}

	@Override
	public void SetNextTestDate(long date) {
		// TODO 自动生成的方法存根
		operate.saveNextTestDate(date, notify);
	}

	@Override
	public void setAutoTestInterval(long interval) {
		// TODO 自动生成的方法存根
		operate.saveAutoTestInterval(interval, notify);
	}
	
	class NotifyDetailAutoTestInfo implements NotifyAutoTestChanged{

		@Override
		public void onComplete(boolean enable, long date, long interval) {
			// TODO 自动生成的方法存根
			if (view!=null) {
				view.showAutoInfo(enable, date, interval);
			}			
		}		
	}
}
