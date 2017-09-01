package cn.com.grean.model;


public interface MeasureModel {
	//加载数据
	void loadMeasure (MeasureOnLoadListener listener);
	interface MeasureOnLoadListener{
		void onComplete(MeasureInfo measureInfo);
	}
	
	String getUpdataURL();

}
