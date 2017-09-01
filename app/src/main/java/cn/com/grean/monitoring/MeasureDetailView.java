package cn.com.grean.monitoring;

import cn.com.grean.model.TestDetailInfo;

public interface MeasureDetailView {
	void showDetail(TestDetailInfo info);
	void showAutoInfo(boolean enable,long date,long interval);
	
}
