package cn.com.grean.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import cn.com.grean.DbTask;
import cn.com.grean.myApplication;
import cn.com.grean.tools;

public class DetailLog implements DetailDataBaseTextModel{
	private final static String tag = "DetailLog";
	private List<String> textList =  new ArrayList<String>();
	private long start,end;
	
	public DetailLog() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public List<String> loadDataBase(Long from, Long to) {
		// TODO 自动生成的方法存根
		
		String statement;
		if (from>to) {
			this.start = to;
			this.end = from;
			statement = "date<" +from.toString()+" and date>"+to.toString();
		}
		else{
			this.start = from;
			this.end = to;
			statement = "date>" +from.toString()+" and date<"+to.toString();
		}
		Log.d(tag, tools.timestamp2string(from)+";"+tools.timestamp2string(to));
		myApplication.getInstance().loadDataBase("log", statement, textList, DbTask.LOG_CONTENT,DbTask.LOG_DATE);
		/*for (int i = 0; i < textList.size(); i++) {
			Log.d(tag, textList.get(i));
			
		}*/
		Log.d(tag, "日志读完");
		return textList;
	}

	@Override
	public void clearList() {
		// TODO 自动生成的方法存根
		textList.clear();
	}

	

	@Override
	public int getDataViewCont() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public ArrayList<Float> getDataViewData() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public ArrayList<String> getDataViewDate() {
		// TODO 自动生成的方法存根
		return null;
	}

	


}
