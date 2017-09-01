package cn.com.grean.Presenter;

import cn.com.grean.model.DetailLogManager;
import cn.com.grean.model.DetailLogManagerModel;

public class LogFragmentManager implements LogFragmentPresenter{
	private DetailLogManagerModel model = new DetailLogManager();

	public LogFragmentManager() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean ExportAllData() {
		// TODO 自动生成的方法存根
		return model.exportAllData();
	}

	@Override
	public void DeleteAllData() {
		// TODO 自动生成的方法存根
		model.deleteAllData();
	}

}
