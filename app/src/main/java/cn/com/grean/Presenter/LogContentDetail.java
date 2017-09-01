package cn.com.grean.Presenter;

import java.util.List;

import android.util.Log;

import cn.com.grean.tools;
import cn.com.grean.model.DetailDataBaseTextModel;
import cn.com.grean.model.DetailLog;
import cn.com.grean.monitoring.LogDetailFragmentView;

public class LogContentDetail implements DetailLogContentPresenter{
	private final static String tag = "LogContentDetail";
	private DetailDataBaseTextModel model = new DetailLog();
	private LogDetailFragmentView view;
	long from,to;
	private List<String> textList;	
	
	public LogContentDetail(LogDetailFragmentView logDetailFragmentView) {
		// TODO 自动生成的构造函数存根
		view = logDetailFragmentView;
	}

	@Override
	public void showFirst() {
		// TODO 自动生成的方法存根
		from = tools.nowtime2timestamp();
		to = from - 7*24*60*60000l;
		Log.d(tag, "开始读日志");
		textList = model.loadDataBase(from, to);
		if (view!=null) {
			view.loadText(textList);
		}
	}

	@Override
	public void getDownData() {
		// TODO 自动生成的方法存根
		
		long index = to;
		to=index - 7*24*60*60000l;
		textList = model.loadDataBase(index, to);
		if (view!=null) {
			view.loadText(textList);
		}
	}

	@Override
	public void getUpData() {
		// TODO 自动生成的方法存根
		model.clearList();
		from = tools.nowtime2timestamp();
		to = from - 7*24*60*60000l;
		textList = model.loadDataBase(from, to);
		if (view!=null) {
			view.loadText(textList);
		}
	}

	@Override
	public String getStartDateString() {
		// TODO 自动生成的方法存根
		return tools.timestamp2string(to);
	}

	@Override
	public String getEndDateString() {
		// TODO 自动生成的方法存根
		return tools.timestamp2string(from);
	}

	@Override
	public void searchLog(long frome, long to) {
		// TODO 自动生成的方法存根
		this.from = frome;
		this.to = to;
		model.clearList();
		textList = model.loadDataBase(from, to);
		if (view!=null) {
			view.loadText(textList);
		}
	}

}
