package cn.com.grean.monitoring;

import java.lang.reflect.Field;

import cn.com.grean.Presenter.CalculateDetail;
import cn.com.grean.Presenter.DetailCalculatePresenter;
import cn.com.grean.Presenter.DetailCommunicationPresenter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateFragment extends Fragment implements CalculateDetailFragmentView,OnClickListener{
	private EditText etSlope;
	private EditText etIntercept;
	private Button btnSaveCoefficient;
	private TextView tvNowResult;
	private DetailCalculatePresenter presenter;
	private EditText etLowCalTimes;
	private EditText etHighCalTimes;
	private Button btnSaveCalTimes;
	
	public CalculateFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_calculate, container, false);
		initView(view);
		presenter = new CalculateDetail(this);
		presenter.showDetail();
		return view;
	}
	
	void initView(View v){
		etSlope =  (EditText) v.findViewById(R.id.etSettingConsumerSlope);
		etIntercept =  (EditText) v.findViewById(R.id.etSettingConsumerIntercept);
		btnSaveCoefficient = (Button) v.findViewById(R.id.btnSettingSaveConsumer);
		tvNowResult = (TextView) v.findViewById(R.id.tvSettingNowValue);
		etHighCalTimes = (EditText) v.findViewById(R.id.etSettingHighCalTimes);
		etLowCalTimes = (EditText) v.findViewById(R.id.etSettingLowCalTimes);
		btnSaveCalTimes = (Button) v.findViewById(R.id.btnSettingSaveCalTimes);
		btnSaveCoefficient.setOnClickListener(this);
		btnSaveCalTimes.setOnClickListener(this);
	}

	

	@Override
	public void updataNowResult(String text) {
		// TODO 自动生成的方法存根
		tvNowResult.setText(text);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnSettingSaveConsumer:
			float slope = Float.valueOf(etSlope.getText().toString());
			float intercept = Float.valueOf(etIntercept.getText().toString());
			if(presenter.saveConsumerCoefficient(slope, intercept)){
				Toast.makeText(getActivity(), "参数设置成功",Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "参数超范围,请重新设置",Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.btnSettingSaveCalTimes:
			int lowTimes = Integer.valueOf(etLowCalTimes.getText().toString());
			int highTimes = Integer.valueOf(etHighCalTimes.getText().toString());
			if(presenter.saveCalibrationTimes(lowTimes, highTimes)){
				Toast.makeText(getActivity(), "参数设置成功",Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "参数超范围,请重新设置",Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void showInfo(String slope, String intercept, String result, String lowCalTimes, String highCalTimes) {
		// TODO 自动生成的方法存根
		etSlope.setText(slope);
		etIntercept.setText(intercept);
		tvNowResult.setText(result);
		etHighCalTimes.setText(highCalTimes);
		etLowCalTimes.setText(lowCalTimes);
	}
	
	

}
