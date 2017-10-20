package cn.com.grean.model;

import cn.com.grean.myApplication;
import android.util.Log;

public class MeasureModelImp implements MeasureModel{
	private final static String tag = "MeasureModelImp";
	private String url;

	public MeasureModelImp() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void loadMeasure(MeasureOnLoadListener listener) {
		// TODO 自动生成的方法存根
		myApplication app = myApplication.getInstance();
		app.loadLastResult();
		MeasureInfo measureInfo = new MeasureInfo(app.getLastResult(),app.getLastDate(),app.getConfigBoolean("AutoTestModel"),
				app.getConfigLong("AutoTestDate"),app.getConfigBoolean("AutoCalModel"),app.getConfigLong("AutoCalDate"));
        app.loadLastResult(app.getSampleNumber());
		measureInfo.setResults(app.getSampleNumber(),app.getLastResults(),app.getEquipmentInfo());
		url = app.getConfigString("URL");
		if (url == "") {
			url = "http://192.168.168.61:12599/Monitoring.apk";
		}
		//回调
		if(listener != null){
			listener.onComplete(measureInfo);
		}
	}

	@Override
	public String getUpdataURL() {
		// TODO 自动生成的方法存根
		return url;
	}

}
