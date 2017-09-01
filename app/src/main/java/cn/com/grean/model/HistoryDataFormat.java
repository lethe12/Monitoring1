package cn.com.grean.model;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import cn.com.grean.tools;

/**
 * 历史数据格式
 * @author Administrator
 *
 */
public class HistoryDataFormat {
	private final static String tag = "HistoryDataFormat";
	private int count;
	private ArrayList<Float> data = new ArrayList<Float>();
	private ArrayList<Long> date = new ArrayList<Long>();
	private ArrayList<String> memo= new ArrayList<String>();
	
	public HistoryDataFormat() {
		// TODO 自动生成的构造函数存根
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public ArrayList<Float> getData() {
		return data;
	}
	public void setData(ArrayList<Float> data) {
		this.data = data;
	}
	
	public ArrayList<String> getMemo() {
		return memo;
	}
	public void setMemo(ArrayList<String> memo) {
		this.memo = memo;
	}
	public ArrayList<Long> getDate() {
		return date;
	}
	
	public ArrayList<String> getDateStrings() {
		ArrayList<String> text=new ArrayList<String>();
		for (int i = 0; i < count; i++) {
			text.add(tools.timestamp2string(date.get(i)));
			
		}
		return text;
	}
	
	public void setDate(ArrayList<Long> date) {
		this.date = date;
	}
	
	public void transformDataText(List<String> list){
		for (int i = 0; i < count; i++) {
			list.add(tools.timestamp2string(date.get(i))+memo.get(i));
		}
	}
}
