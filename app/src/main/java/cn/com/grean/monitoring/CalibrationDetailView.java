package cn.com.grean.monitoring;

import cn.com.grean.model.CalDetailInfo;

public interface CalibrationDetailView {
	void showDetail(CalDetailInfo info);
	void showAutoInfo(boolean enable,long date,long interval);

}
