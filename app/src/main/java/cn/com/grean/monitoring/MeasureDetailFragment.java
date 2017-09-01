package cn.com.grean.monitoring;

import java.util.Calendar;

import cn.com.grean.tools;
import cn.com.grean.Presenter.MeasureDetail;
import cn.com.grean.Presenter.MeasureDetailPresenter;
import cn.com.grean.model.TestDetailInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class MeasureDetailFragment extends Fragment implements OnClickListener,MeasureDetailView{
	private final static String tag = "MeasureDetailFragment";
	private Button btnTestOnce;
	private Button btnTestStandard;
	private TextView tvMeasureInfo;
	private View llNexAutoTestInfo;
	private TextView tvNextTestDate;
	private Button btnSaveAutoTestInfo;

	private ToggleButton tbAutoTest;
	private Button btnStop;
	private MeasureDetailPresenter presenter;
	private EditText etAutoTestInterval;

	public MeasureDetailFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_measure, container, false);
		
		//Log.d(tag, "开始");
		initView(view);		
		presenter = new MeasureDetail(this);
		presenter.ShowInfo();
		Log.d(tag, "加载完");
		
		return view;
	}
	
	private void initView(View v){
		btnTestOnce = (Button) v.findViewById(R.id.btn_operation_testonce);
		btnTestStandard=(Button) v.findViewById(R.id.btn_operation_teststandard);
		tvMeasureInfo=(TextView) v.findViewById(R.id.tv_operate_last_measureinfo);
		llNexAutoTestInfo = v.findViewById(R.id.linearLayoutNextTest);
		tvNextTestDate = (TextView) v.findViewById(R.id.tvNextTestDate);
		tbAutoTest = (ToggleButton) v.findViewById(R.id.tbAutoTestSet);
		btnStop = (Button) v.findViewById(R.id.btnOperationStop);
		etAutoTestInterval = (EditText) v.findViewById(R.id.etAutoTestInterval);
		btnSaveAutoTestInfo = (Button) v.findViewById(R.id.btnSaveAutoInfo);
		btnTestOnce.setOnClickListener(this);
		btnTestStandard.setOnClickListener(this);
		tvNextTestDate.setOnClickListener(this);
		btnSaveAutoTestInfo.setOnClickListener(this);
		tbAutoTest.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		//Log.d(tag, "结束");
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_operation_testonce:
			if(presenter.TestOnce()){
				Toast.makeText(getActivity(), "开始测量", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_operation_teststandard:
			if(presenter.TestStandard()){
				Toast.makeText(getActivity(), "开始测量标液", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnOperationStop:
			presenter.StopTest();
			Toast.makeText(getActivity(), "已停止当前流程", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tbAutoTestSet:	
			presenter.SwitchAutoTestModel(tbAutoTest.isChecked());
			
			break;
		case R.id.tvNextTestDate:
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
			
			/*final int inType = etAutoTestInterval.getInputType();
			etAutoTestInterval.setInputType(InputType.TYPE_NULL);
			//etAutoTestInterval.onTouchEvent(event);
			etAutoTestInterval.setInputType(inType);
			etAutoTestInterval.setSelection(etAutoTestInterval.getText().length());*/

			builder.setTitle("设置下次测量时间");

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
					//long l = tools.string2timestamp(timeString);
					presenter.SetNextTestDate(tools.string2timestamp(timeString));
					// 保存配置信息
					dialog.cancel();
				}
			});
		

			Dialog dialog = builder.create();
			dialog.show();
			break;
		case R.id.btnSaveAutoInfo:
			long interval = Integer.valueOf(etAutoTestInterval.getText().toString());
			if ((interval < 50)||(interval > 1440)) {
				Toast.makeText(getActivity(), "时间间隔超范围，设置失败",Toast.LENGTH_SHORT ).show();
			}
			else {				
				presenter.setAutoTestInterval(interval*60000l);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void showDetail(TestDetailInfo info) {
		// TODO 自动生成的方法存根
		tvMeasureInfo.setText(info.getLastTestInfo());
		if (info.isAutoEnable()) {
			llNexAutoTestInfo.setVisibility(View.VISIBLE);
		
		}
		else {
			llNexAutoTestInfo.setVisibility(View.INVISIBLE);
			
		}
		tvNextTestDate.setText(info.getNextTastDate());
		etAutoTestInterval.setText(info.getAutoTestInterval());
		tbAutoTest.setChecked(info.isAutoEnable());
	}

	@Override
	public void showAutoInfo(boolean enable, long date, long interval) {
		// TODO 自动生成的方法存根
		if (enable) {
			llNexAutoTestInfo.setVisibility(View.VISIBLE);
			Toast.makeText(getActivity(), "连续测量开启", Toast.LENGTH_SHORT).show();
		}
		else {
			llNexAutoTestInfo.setVisibility(View.INVISIBLE);
			Toast.makeText(getActivity(), "连续测量关闭", Toast.LENGTH_SHORT).show();
		}
		String dateString ="下次测量时间:"+ tools.timestamp2string(date);
		tvNextTestDate.setText(dateString);
		String string = String.valueOf(interval/60000l);
		etAutoTestInterval.setText(string);
		Intent intent = new Intent();
		intent.setAction("UpdatAutoInfo");
		intent.putExtra("enable", enable);
		intent.putExtra("date", date);
		intent.putExtra("interval", interval);
		getActivity().sendBroadcast(intent);
		Intent intent2 = new Intent();
		intent2.setAction("nextTest");
		if (enable) {			
			intent2.putExtra("dateString", dateString);
		}
		else{
			intent2.putExtra("dateString", "下次测量时间:");
		}
		getActivity().sendBroadcast(intent2);
				
	}
	
	

}
