package cn.com.grean.monitoring;

import java.lang.reflect.Field;
import java.util.Calendar;

import cn.com.grean.tools;
import cn.com.grean.Presenter.CalibrationDetail;
import cn.com.grean.Presenter.CalibrationDetailPresenter;
import cn.com.grean.model.CalDetailInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CalibrationDetailFragment extends Fragment implements OnClickListener,CalibrationDetailView{
	private final static String tag = "CalibrationDetailFragment";
	private Button btnCalHigh;
	private Button btnCalLow;
	private Button btnCal;
	private TextView tvCalInfo;
	private EditText etCalLow;
	private EditText etCalHigh;
	private ToggleButton tbAutoCal;
	private TextView tvAutoCalNextDate;
	private EditText etAutoCalInterval;
	private Button btnSaveAutoCal;
	private View llAutoCalInfo;
	private CalibrationDetailPresenter presenter;
	
	

	public CalibrationDetailFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_calibration, container, false);
		initView(view);
		presenter = new CalibrationDetail(this);
		presenter.ShowInfo();
		return view;
	}
	
	private void initView(View v){
		btnCal=(Button) v.findViewById(R.id.btnOperationCal);
		btnCalHigh=(Button) v.findViewById(R.id.btnOperationCalHigh);
		btnCalLow=(Button) v.findViewById(R.id.btnOperationCalLow);
		btnSaveAutoCal=(Button) v.findViewById(R.id.btnSaveAutoCalInfo);
		tvAutoCalNextDate = (TextView) v.findViewById(R.id.tvNextCalDate);
		tvCalInfo=(TextView) v.findViewById(R.id.tvLastCalInfo);
		etAutoCalInterval =(EditText) v.findViewById(R.id.etAutoCalInterval);
		etCalHigh=(EditText) v.findViewById(R.id.etCalHigh);
		etCalLow=(EditText) v.findViewById(R.id.etCalLow);
		tbAutoCal=(ToggleButton) v.findViewById(R.id.tbAutoCalSet);
		llAutoCalInfo = v.findViewById(R.id.linearLayoutNextCal);
		btnCal.setOnClickListener(this);
		btnCalHigh.setOnClickListener(this);
		btnCalLow.setOnClickListener(this);
		btnSaveAutoCal.setOnClickListener(this);
		tbAutoCal.setOnClickListener(this);
		tvAutoCalNextDate.setOnClickListener(this);
		btnSaveAutoCal.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		float data;
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnOperationCalLow:
			data = Float.valueOf(etCalLow.getText().toString());
			if(presenter.calLow(data)){
				Toast.makeText(getActivity(), "开始低点校准", Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnOperationCalHigh:
			data = Float.valueOf(etCalHigh.getText().toString());
			if (presenter.calHigh(data)) {
				Toast.makeText(getActivity(), "开始高点校准", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnOperationCal:
			float low = Float.valueOf(etCalLow.getText().toString());
			float high = Float.valueOf(etCalHigh.getText().toString());
			if (presenter.oneKeyToCal(low, high)) {
				Toast.makeText(getActivity(), "开始一键校准", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
			}			
			break;
		case R.id.tbAutoCalSet:
			presenter.SwitchAutoCalModel(tbAutoCal.isChecked());
			break;
		case R.id.tvNextCalDate:
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
			

			builder.setTitle("设置下次校准时间");

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
					//Log.d(tag, timeString);
					//long l = tools.string2timestamp(timeString);
					presenter.SetNextCalDate(tools.string2timestamp(timeString));
					// 保存配置信息
					dialog.cancel();
				}
			});		

			Dialog dialog = builder.create();
			dialog.show();
			break;
		case R.id.btnSaveAutoCalInfo:
			long interval = Integer.valueOf(etAutoCalInterval.getText().toString());
			if ((interval<15)||(interval > 10080)) {
				Toast.makeText(getActivity(), "时间间隔超范围,设置失败", Toast.LENGTH_SHORT).show();
			}
			else {
				presenter.setAutoCalInterval(interval*60000l);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void showDetail(CalDetailInfo info) {
		// TODO 自动生成的方法存根
		tvCalInfo.setText(info.getLastCalInfo());
		if (info.isAutoEnable()) {
			llAutoCalInfo.setVisibility(View.VISIBLE);
		}
		else {
			llAutoCalInfo.setVisibility(View.INVISIBLE);
		}
		tvAutoCalNextDate.setText(info.getNextCalDate());
		etAutoCalInterval.setText(info.getAutoCalInterval());
		etCalLow.setText(info.getLowTarget());
		etCalHigh.setText(info.getHighTarget());
		tbAutoCal.setChecked(info.isAutoEnable());
	}

	@Override
	public void showAutoInfo(boolean enable, long date, long interval) {
		// TODO 自动生成的方法存根
		if (enable) {
			llAutoCalInfo.setVisibility(View.VISIBLE);
			Toast.makeText(getActivity(), "自动校准开启", Toast.LENGTH_SHORT).show();
		}
		else {
			llAutoCalInfo.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(), "自动校准关闭", Toast.LENGTH_SHORT).show();
		}
		String dateString = "下次校准时间:"+tools.timestamp2string(date);
		tvAutoCalNextDate.setText(dateString);
		etAutoCalInterval.setText(String.valueOf(interval/60000l));
		Intent intent = new Intent();
		intent.setAction("UpdatAutoCalInfo");
		intent.putExtra("enable", enable);
		intent.putExtra("date", date);
		intent.putExtra("interval", interval);
		getActivity().sendBroadcast(intent);
		Intent intent2 = new Intent();
		intent2.setAction("nextCal");
		if (enable) {
			intent2.putExtra("dateString", dateString);			
		}
		else {
			intent2.putExtra("dateString",  "下次校准时间:");
		}
		getActivity().sendBroadcast(intent2);
	}
	
	

}
