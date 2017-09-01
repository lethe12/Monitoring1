package cn.com.grean.Presenter;

import java.util.ArrayList;
import java.util.List;

import cn.com.grean.tools;
import cn.com.grean.model.DetailDataBaseTextModel;
import cn.com.grean.model.DetailHistoryData;
import cn.com.grean.monitoring.HistoryFragmentView;

public class HistoryDataDetail implements DetailHistoryDataPresenter{
	private DetailDataBaseTextModel model = new DetailHistoryData();
	private HistoryFragmentView view;
	long from,to;
	private List<String> textList;
	public HistoryDataDetail(HistoryFragmentView historyFragmentView) {
		// TODO 自动生成的构造函数存根
		view = historyFragmentView;
	}

	@Override
	public void showFirst() {
		// TODO 自动生成的方法存根
		from = tools.nowtime2timestamp();
		to = from - 7*24*60*60000l;
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
	public void searchData(long frome, long to) {
		// TODO 自动生成的方法存根
		this.from = frome;
		this.to = to;
		model.clearList();
		textList = model.loadDataBase(from, to);
		if (view!=null) {
			view.loadText(textList);
		}
	}

	@Override
	public void getDataView() {
		// TODO 自动生成的方法存根
		int count = model.getDataViewCont();
		ArrayList<Float> data = model.getDataViewData();
		ArrayList<String> date = model.getDataViewDate();
		if (view!=null) {
			view.showDataView(count, data, date);
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

}
