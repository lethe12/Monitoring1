package cn.com.grean.monitoring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import cn.com.grean.tools;
import cn.com.grean.Presenter.DetailHistoryDataPresenter;
import cn.com.grean.Presenter.HistoryDataDetail;
import cn.com.grean.listviewinfo.OnRefreshListener;
import cn.com.grean.listviewinfo.RefreshListView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class HistoryFragment extends Fragment implements HistoryFragmentView,OnRefreshListener,OnClickListener{
	private final static String tag = "HistoryFragment";
	private List<String> textList;
	private MyAdapter adapter;
	private RefreshListView rListView;
	private DetailHistoryDataPresenter presenter;
	private LineChart mLineChart;
	private TextView tvStartDate;
	private TextView tvEndDate;
	private Button btnSearchData;
	private boolean isStartDate;
	private Button btnDataView;
	private boolean isDataView= false;
	private View lvDataView;

	public HistoryFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_history, container, false);
		rListView = (RefreshListView) view.findViewById(R.id.lvHistoryData);
		mLineChart = (LineChart) view.findViewById(R.id. spread_line_chart);
		tvEndDate = (TextView) view.findViewById(R.id.tvDataEndDate);
		tvStartDate = (TextView) view.findViewById(R.id.tvDataStartDate);
		btnDataView = (Button) view.findViewById(R.id.btnDataView);
		btnSearchData = (Button) view.findViewById(R.id.btnDataSearch);
		lvDataView = view.findViewById(R.id.lvDataView);
		presenter = new HistoryDataDetail(this);
		presenter.showFirst();
		adapter = new MyAdapter();
		rListView.setAdapter(adapter);
		rListView.setOnRefreshListener(this);	
		tvEndDate.setOnClickListener(this);
		tvStartDate.setOnClickListener(this);
		btnDataView.setOnClickListener(this);
		btnSearchData.setOnClickListener(this);
		showSearchDate();
		return view;
	}
	
	private void showSearchDate(){
		tvEndDate.setText(presenter.getEndDateString());
		tvStartDate.setText(presenter.getStartDateString());
	}

	@Override
	public void loadText(List<String> textList) {
		// TODO 自动生成的方法存根
		this.textList = textList;
	}

	@Override
	public void onDownPullRefresh() {
		// TODO 自动生成的方法存根
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO 自动生成的方法存根
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				// TODO 自动生成的方法存根
				super.onPostExecute(result);
				presenter.getUpData();
				adapter.notifyDataSetChanged();
				rListView.hideHeaderView();
				showSearchDate();
			}
			
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{});
	}

	@Override
	public void onLoadingMore() {
		// TODO 自动生成的方法存根
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void... params) {
				// TODO 自动生成的方法存根
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				presenter.getDownData();
				adapter.notifyDataSetChanged();
				rListView.hideFooterView();
				showSearchDate();
			}
			
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[]{});
	}
	
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return textList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return textList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView textView = new TextView(getActivity());
			textView.setText(textList.get(position));
			//textView.setText("1234456");
			textView.setTextColor(Color.BLACK);
			textView.setTextSize(30f);
			return textView;
		}

	}
	
	 /**  
     * 生成一个数据  
     * @param count 表示图表中有多少个坐标点  
     * @param range 用来生成range以内的随机数  
     * @return  
     */    
    private LineData getLineData (Integer count, ArrayList<Float> rangeshu,ArrayList<String> shijian) {    
        ArrayList<String> xValues = new ArrayList<String>(); 
        //把数据取出来
        //count=aadata.get_Aa_show_textdata_num();
        //float[] rangeshu=aadata.get_Aa_show_textdata_quxian();
        
        //结束取数据
        Float value;
        ArrayList<Entry> yValues = new ArrayList<Entry>();         
        
        for (int i = 0; i < count; i++) {    
            // x轴显示的数据，这里默认使用数字下标显示    
            //xValues.add("" + i);  
			//xValues.add(shijian.get(i));
			xValues.add(shijian.get(count-i-1));//逆序
        }    
        // y轴的数据    
        for (int i = 0; i < count; i++) {    
            //value = (float) (Math.random()) + 3;  
        	//value =rangeshu.get(i);
            value =rangeshu.get(count-i-1);//逆序
            yValues.add(new Entry(value, i));    
        } 
    
        // create a dataset and give it a type    
        // y轴的数据集合    
        LineDataSet lineDataSet = new LineDataSet(yValues, "单位 mg/L" /*显示在比例图上*/);    
        // mLineDataSet.setFillAlpha(110);    
        //mLineDataSet.setFillColor(Color.RED);    
    
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(1.75f); // 线宽    
        lineDataSet.setCircleSize(3f);// 显示的圆形大小    
        lineDataSet.setColor(Color.rgb(0, 255, 64));// 显示颜色    
        lineDataSet.setCircleColor(Color.rgb(0, 255, 64));// 圆形的颜色    
        lineDataSet.setHighLightColor(Color.rgb(0, 255, 64)); // 高亮的线的颜色    
        
        lineDataSet.setDrawCircles(true);//图标上的数据点不用小圆圈表示
        lineDataSet.setDrawCubic(true);//设置允许曲线平滑
        lineDataSet.setCubicIntensity(0.1f);//设置平滑度
        lineDataSet.setDrawFilled(true);//设置允许     填充
        //lineDataSet.setFillColor(Color.rgb(0, 255, 255));//设置填充的颜色
        lineDataSet.setFillColor(Color.rgb(0, 255, 64));//设置填充的颜色
        
        lineDataSet.setValueTextSize(1f);
        
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();    
        lineDataSets.add(lineDataSet); // add the datasets    
    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);    
    
        return lineData;    
    }
    
 // 设置显示的样式    
    private void showChart(LineChart lineChart, LineData lineData, int color) {    
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框    
    
        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("无历史数据");    
            
        // enable / disable grid background    
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色    
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
        //lineChart.setGridBackgroundColor(Color.GREEN & 0x70FFFFFF); 
        // enable touch gestures    
        lineChart.setTouchEnabled(true); // 设置是否可以触摸    
    
        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    
    
        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);// 
        
    
        lineChart.setBackgroundColor(color);// 设置背景    
    
        // add data    
        lineChart.setData(lineData); // 设置数据    
    
        // get the legend (only possible after setting data)    
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的    
    
        // modify the legend ...    
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
        mLegend.setForm(LegendForm.CIRCLE);// 样式    
        mLegend.setFormSize(6f);// 字体    
        mLegend.setTextColor(Color.BLACK);// 颜色    
        //mLegend.setTextColor(Color.GREEN);
//      mLegend.setTypeface(mTf);// 字体    
       
        YAxis axisLeft = lineChart.getAxisLeft(); //y轴左边标示
        YAxis axisRight = lineChart.getAxisRight(); //y轴右边标示
        YAxisValueFormatter f=new DefaultYAxisValueFormatter(2);
        axisLeft.setValueFormatter(f);
        axisRight.setValueFormatter(f);
        
        XAxis axisDown = lineChart.getXAxis();
        axisDown.setPosition(XAxis.XAxisPosition.BOTTOM); //x轴位置
       
    
       // lineChart.animateX(2500); // 立即执行的动画,x轴    
        lineChart.animateX(500); 
      }

	@Override
	public void showDataView(int count, ArrayList<Float> data,ArrayList<String> date) {
		// TODO 自动生成的方法存根

		LineData lineData = getLineData(count, data, date);
		//LineData lineData = getLineData(10, data, date);
		showChart(mLineChart, lineData,  Color.rgb(204, 204, 204));
		/*LineData lineData2 = getLineData(10, data, date);
		showChart(mLineChart, lineData2,  Color.rgb(100, 100, 100));*/
		
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.tvDataEndDate:
			isStartDate = false;
			setSearchDate();	
			break;
		case R.id.tvDataStartDate:
			isStartDate = true;
			setSearchDate();
			break;
		case R.id.btnDataSearch:
			isDataView = false;
			btnDataView.setText("曲线");
			rListView.setVisibility(View.VISIBLE);
			lvDataView.setVisibility(View.GONE);
			long frome = tools.string2timestamp(tvEndDate.getText().toString());
			long to = tools.string2timestamp(tvStartDate.getText().toString());
			presenter.searchData(frome, to);
			adapter.notifyDataSetChanged();
			break;
		case R.id.btnDataView:
			if (!isDataView) {	
				isDataView = true;
				btnDataView.setText("列表");
				rListView.setVisibility(View.GONE);
				lvDataView.setVisibility(View.VISIBLE);
				presenter.getDataView();
			}
			else {
				isDataView = false;
				btnDataView.setText("曲线");
				rListView.setVisibility(View.VISIBLE);
				lvDataView.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

	private void setSearchDate(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		View view = View.inflate(getActivity(), R.layout.date_time_dialog, null);
		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
		final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
		builder.setView(view);

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(Calendar.MINUTE);

		builder.setTitle("设置查询时间");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				String timeString;
				StringBuffer sb = new StringBuffer();
				sb.append(String.format("%d-%02d-%02d %02d:%02d", datePicker.getYear(), datePicker.getMonth() + 1,
						datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
				timeString = sb.toString();
				Log.d(tag, timeString);

				//tv.setText(timeString);
				if (isStartDate) {					
					tvStartDate.setText(timeString);
				}
				else {					
					tvEndDate.setText(timeString);
				}
				// 保存配置信息
				dialog.cancel();
			}
		});
	

		Dialog dialog = builder.create();
		dialog.show();
	}

}
