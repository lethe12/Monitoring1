package cn.com.grean.monitoring;

import java.lang.reflect.Field;
import java.util.Calendar;

import cn.com.grean.tools;
import cn.com.grean.Presenter.DetailSystemPresenter;
import cn.com.grean.Presenter.SystemDetail;
import cn.com.grean.model.DetailSystem;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class SystemFragment extends Fragment implements SystemFragmentView,OnClickListener{
	private final static String tag = "SystemFragment";
	private Spinner spBackLight;
	private DigitalClock clock;
	private DetailSystemPresenter presenter;
	private TextView tvSystemDate;

	public SystemFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_system, container, false);
		initView(view);
		presenter = new SystemDetail(this);
		presenter.showDitail();
		return view;
	}
	
	void initView(View v){
		spBackLight= (Spinner) v.findViewById(R.id.spBackLight);
		clock=(DigitalClock) v.findViewById(R.id.digitalSystemClock);
		tvSystemDate=(TextView) v.findViewById(R.id.tvSettingSystemDate);
		tvSystemDate.setOnClickListener(this);
		clock.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
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

		builder.setTitle("设置下系统量时间");

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
				sb.append(String.format("%d-%02d-%02d %02d:%02d", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
				timeString = sb.toString();
				Log.d(tag, timeString);
				int year = datePicker.getYear();
				int month = datePicker.getMonth()+1;
				int day = datePicker.getDayOfMonth();
				int hour = timePicker.getCurrentHour();
				int min = timePicker.getCurrentMinute();
				//long l = tools.string2timestamp(timeString);
				presenter.saveSystemTime(year, month, day, hour, min);
				// 保存配置信息
				dialog.cancel();
			}
		});
	

		Dialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void showBackLightTime(int pos, String date) {
		// TODO 自动生成的方法存根
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.system_spnner, presenter.getBackLightTimeString());
		spBackLight.setAdapter(adapter);
		spBackLight.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO 自动生成的方法存根
				presenter.showBackLightTime(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO 自动生成的方法存根
				
			}
		});
		
		spBackLight.setSelection(pos);
		tvSystemDate.setText(date);
	}
	


}
