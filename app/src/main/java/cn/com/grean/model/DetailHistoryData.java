package cn.com.grean.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultYAxisValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import cn.com.grean.DbTask;
import cn.com.grean.myApplication;
/**
 * 获取历史数据
 * @author Administrator
 *
 */
public class DetailHistoryData implements DetailDataBaseTextModel{
	private List<String> textList =  new ArrayList<String>();
	private HistoryDataFormat mHistoryDataFormat;
	public DetailHistoryData() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public List<String> loadDataBase(Long from, Long to) {
		// TODO 自动生成的方法存根
		String statement;
		if (from>to) {
			statement = "date<" +from.toString()+" and date>"+to.toString();
		}
		else{
			statement = "date>" +from.toString()+" and date<"+to.toString();
		}
		
		mHistoryDataFormat = myApplication.getInstance().loadHistoryData(statement);
		mHistoryDataFormat.transformDataText(textList);
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
		if (mHistoryDataFormat!=null) {
			return mHistoryDataFormat.getCount();
		}
		else {
			
			return 0;
		}
	}

	@Override
	public ArrayList<Float> getDataViewData() {
		// TODO 自动生成的方法存根
		if (mHistoryDataFormat!=null) {
			return mHistoryDataFormat.getData();
		}
		else {
			
			return null;
		}
	}

	@Override
	public ArrayList<String> getDataViewDate() {
		// TODO 自动生成的方法存根
		if (mHistoryDataFormat!= null) {
			return mHistoryDataFormat.getDateStrings();
		}
		else {
			
			return null;
		}
	} 


}
