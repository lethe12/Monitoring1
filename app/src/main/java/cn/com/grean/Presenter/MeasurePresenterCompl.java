package cn.com.grean.Presenter;

import cn.com.grean.model.MeasureInfo;
import cn.com.grean.model.MeasureModel;
import cn.com.grean.model.MeasureModel.MeasureOnLoadListener;
import cn.com.grean.model.MeasureModelImp;
import cn.com.grean.monitoring.MeasureView;

public class MeasurePresenterCompl implements MeasurePresenter{
	MeasureModel mMeasureModel = new MeasureModelImp();
	
	MeasureView mMeasureView;

	public MeasurePresenterCompl(MeasureView mMeasureView) {
		// TODO 自动生成的构造函数存根
		this.mMeasureView = mMeasureView;
	}

	@Override
	public void fetch() {
		// TODO 自动生成的方法存根
		if (mMeasureModel != null) {
			mMeasureModel.loadMeasure(new MeasureOnLoadListener() {			

				@Override
				public void onComplete(MeasureInfo measureInfo) {
					// TODO 自动生成的方法存根
					mMeasureView.showMeasureInfo(measureInfo);
				}
			});
		}
	}

	@Override
	public String getUpdataURL() {
		// TODO 自动生成的方法存根
		return mMeasureModel.getUpdataURL();
	}

}
