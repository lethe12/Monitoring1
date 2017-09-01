package cn.com.grean.model;

import java.util.ArrayList;
import java.util.List;

public interface DetailDataBaseTextModel {
	List<String> loadDataBase(Long from,Long to);//导出日志 时间从 from 到 to
	void clearList();//清除list中的数据
	/**
	 * 获取曲线的数量
	 * @return 
	 */
	int getDataViewCont();
	/**
	 * 曲线数据
	 * @return
	 */
	ArrayList<Float> getDataViewData();
	/**
	 * 曲线的日期
	 * @return
	 */
	ArrayList<String> getDataViewDate();
}
