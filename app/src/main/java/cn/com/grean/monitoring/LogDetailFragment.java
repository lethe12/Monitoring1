package cn.com.grean.monitoring;

import java.util.Calendar;
import java.util.List;

import cn.com.grean.tools;
import cn.com.grean.Presenter.DetailLogContentPresenter;
import cn.com.grean.Presenter.LogContentDetail;
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

public class LogDetailFragment extends Fragment implements OnRefreshListener,LogDetailFragmentView,OnClickListener{
	private final static String tag = "LogDetailFragment";
	private List<String> textList;
	private MyAdapter adapter;
	private RefreshListView rListView;
	private DetailLogContentPresenter presenter;
	private TextView tvStartDate;
	private TextView tvEndDate;
	private Button btnSearchLog;
	private boolean isStartDate;
	
	public LogDetailFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_log, container, false);
		rListView = (RefreshListView) view.findViewById(R.id.lvLogContent);
		tvEndDate = (TextView) view.findViewById(R.id.tvLogEndDate);
		tvStartDate = (TextView) view.findViewById(R.id.tvLogStartDate);
		btnSearchLog = (Button) view.findViewById(R.id.btnLogSearch);
		btnSearchLog.setOnClickListener(this);
		tvEndDate.setOnClickListener(this);
		tvStartDate.setOnClickListener(this);
		presenter = new LogContentDetail(this);
		presenter.showFirst();
		adapter = new MyAdapter();
		rListView.setAdapter(adapter);
		rListView.setOnRefreshListener(this);
		showSearchDate();
		return view;
	}
	
	private void showSearchDate(){
		tvEndDate.setText(presenter.getEndDateString());
		tvStartDate.setText(presenter.getStartDateString());
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

	@Override
	public void loadText(List<String> textList) {
		// TODO 自动生成的方法存根
		this.textList = textList;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnLogSearch:
			long frome = tools.string2timestamp(tvEndDate.getText().toString());
			long to = tools.string2timestamp(tvStartDate.getText().toString());
			presenter.searchLog(frome, to);
			adapter.notifyDataSetChanged();
			//showSearchDate();
			break;
		case R.id.tvLogEndDate:
			isStartDate = false;
			setSearchDate();			
			break;
		case R.id.tvLogStartDate:
			isStartDate = true;
			setSearchDate();
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
