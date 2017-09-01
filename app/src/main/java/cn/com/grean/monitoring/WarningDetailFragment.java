package cn.com.grean.monitoring;

import java.lang.reflect.Field;

import cn.com.grean.Presenter.DetailWarningPresenter;
import cn.com.grean.Presenter.WarningDetail;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WarningDetailFragment extends Fragment implements WarningDetailFragmentView,OnClickListener{
	private EditText etWarningFloor;
	private EditText etWarningUpper;
	private Button btnSaveWarning;
	private Button btnClearWarning;
	private TextView tvWarningInfo;
	private DetailWarningPresenter presenter;
	public WarningDetailFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_warning, container, false);
		initView(view);
		presenter = new WarningDetail(this);
		presenter.showWarningInfo();
		return view;
	}
	
	void initView(View v){
		etWarningFloor = (EditText) v.findViewById(R.id.etWarningFloor);
		etWarningUpper = (EditText) v.findViewById(R.id.etWarningUpper);
		btnSaveWarning = (Button) v.findViewById(R.id.btnSaveWarning);
		btnClearWarning = (Button) v.findViewById(R.id.btnWarningClear);
		tvWarningInfo = (TextView) v.findViewById(R.id.tvWarningInfo);
		btnClearWarning.setOnClickListener(this);
		btnSaveWarning.setOnClickListener(this);
	}
	
	@Override
	public void showWarningInfo(String info) {
		// TODO 自动生成的方法存根
		tvWarningInfo.setText(info);
	}

	@Override
	public void showFloor(String floor) {
		// TODO 自动生成的方法存根
		etWarningFloor.setText(floor);
	}

	@Override
	public void showUpper(String upper) {
		// TODO 自动生成的方法存根
		etWarningUpper.setText(upper);
	}

	@Override
	public void clearInfo() {
		// TODO 自动生成的方法存根
		tvWarningInfo.setText("");
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnWarningClear:
			presenter.clearWarningInfo();
			break;
		case R.id.btnSaveWarning:
			float floor = Float.valueOf(etWarningFloor.getText().toString());
			float upper = Float.valueOf(etWarningUpper.getText().toString());
			presenter.setWarningInfo(floor, upper);
			break;

		default:
			break;
		}
	}
	

}
