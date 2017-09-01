package cn.com.grean.monitoring;

import java.util.ArrayList;
import java.util.List;

public interface HistoryFragmentView {
	void loadText(List<String> textList);
	/**
	 * 显示数据
	 * @param count 总点数
	 * @param data 需要显示的数据
	 * @param date 需要显示的日期
	 */
	void showDataView(int count,ArrayList<Float> data,ArrayList<String> date);
}
