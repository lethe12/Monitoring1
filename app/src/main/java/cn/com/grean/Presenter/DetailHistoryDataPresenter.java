package cn.com.grean.Presenter;

public interface DetailHistoryDataPresenter {
	void showFirst();//显示初次进入页面时的数据
	void getDownData();//获取下一个周期数据
	void getUpData();//取得最新数据
	void searchData(long frome,long to);
	/**
	 *获取历史数据曲线
	 */
	void getDataView();
	/**
	 * 获取当前查询的起始时间的字符串
	 * @return
	 */
	String getStartDateString();
	/**
	 * 获取当前查询终止时间的字符串
	 * @return
	 */
	String getEndDateString(); 
}
