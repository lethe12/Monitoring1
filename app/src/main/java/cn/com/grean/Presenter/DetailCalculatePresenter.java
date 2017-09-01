package cn.com.grean.Presenter;

public interface DetailCalculatePresenter {
	void showDetail();
	boolean saveConsumerCoefficient(float slope,float intercept);
	boolean saveCalibrationTimes(int low,int high);
}
