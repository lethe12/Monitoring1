package cn.com.grean.Presenter;

public interface DetailLogContentPresenter {
	void showFirst();//显示初次进入页面时的数据
	void getDownData();//获取下一个周期数据
	void getUpData();//取得最新数据
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
	/**
	 * 查询日志
	 * @param frome 起始时间戳  较大
	 * @param to	终止时间戳 较小
	 */
	void searchLog(long frome,long to);
}
