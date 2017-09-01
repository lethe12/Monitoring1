package cn.com.grean.monitoring;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class OperationFragment extends Fragment implements OnClickListener{
	@SuppressWarnings("unused")
	private final static String tag="OperationFragment";
	private View measureDetail;
	private View calibrationDetail;
	private View maintenanceDetail;
	private View warningDetail;
	private TextView tvMeasure;
	private TextView tvCalibration;
	private TextView tvMaintance;
	private TextView tvWarring;
	
	private MeasureDetailFragment measureDetailFragment;
	private CalibrationDetailFragment calibrationDetailFragment;
	private MaintenanceDetailFragment maintenanceDetailFragment;
	private WarningDetailFragment warningDetailFragment;
	
	private FragmentManager fragmentManager; 
	private Fragment lastFragment; 

	public OperationFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View messageLayout = inflater.inflate(R.layout.activity_operation, container, false);
		
		initView(messageLayout);		
		fragmentManager = getActivity().getFragmentManager();  
		//fragmentManager = getFragmentManager();  
        setTabSelection(0);
		return messageLayout;
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.operation_menu_one:
			setTabSelection(0);
			break;
		case R.id.operation_menu_two:
			setTabSelection(1);
			break;
		case R.id.operation_menu_three:
			setTabSelection(2);
			break;
		case R.id.operation_menu_fourth:
			setTabSelection(3);
			break;

		default:
			break;
		}
	}
	
	private void initView(View messageLayout){
		measureDetail = messageLayout.findViewById(R.id.operation_menu_one);
		calibrationDetail = messageLayout.findViewById(R.id.operation_menu_two);
		maintenanceDetail = messageLayout.findViewById(R.id.operation_menu_three);
		warningDetail = messageLayout.findViewById(R.id.operation_menu_fourth);
		measureDetail.setOnClickListener(this);
		calibrationDetail.setOnClickListener(this);
		maintenanceDetail.setOnClickListener(this);
		warningDetail.setOnClickListener(this);
		tvCalibration = (TextView) messageLayout.findViewById(R.id.tvOperateCalibration);
		tvMaintance = (TextView) messageLayout.findViewById(R.id.tvOperateMaintance);
		tvMeasure = (TextView) messageLayout.findViewById(R.id.tvOperateMeasure);
		tvWarring = (TextView) messageLayout.findViewById(R.id.tvOperateWarring);
	}
	

	/**
	 * 
	 * @param index =0 测量碎片 =1 校准 碎片 =2 维护碎片
	 */
	private void setTabSelection(int index){
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();  
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        //hideFragments(transaction); 
        switch (index) {
		case 0:
			tvMeasure.setTextColor(Color.parseColor("#1296DB"));
			measureDetail.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if(measureDetailFragment == null){
				measureDetailFragment = new MeasureDetailFragment();
			}
			//transaction.attach(measureDetailFragment).commit();
			transaction.replace(R.id.details_layout, measureDetailFragment,"measureDetailFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("measureDetailFragment");
			break;
		case 1:		
			tvCalibration.setTextColor(Color.parseColor("#1296DB"));
			calibrationDetail.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (calibrationDetailFragment == null) {				
				calibrationDetailFragment = new CalibrationDetailFragment();
			}
			//transaction.attach(calibrationDetailFragment);
			transaction.replace(R.id.details_layout, calibrationDetailFragment,"calibrationDetailFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("calibrationDetailFragment");
			break;
		case 2:
			tvMaintance.setTextColor(Color.parseColor("#1296DB"));
			maintenanceDetail.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (maintenanceDetailFragment == null) {				
				maintenanceDetailFragment = new MaintenanceDetailFragment();
			}
			//transaction.attach(maintenanceDetailFragment);
			transaction.replace(R.id.details_layout, maintenanceDetailFragment,"maintenanceDetailFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("maintenanceDetailFragment");
			break;
		case 3:
			tvWarring.setTextColor(Color.parseColor("#1296DB"));
			warningDetail.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (warningDetailFragment == null) {
				warningDetailFragment = new WarningDetailFragment();
			}
			//transaction.attach(warningDetailFragment);
			transaction.replace(R.id.details_layout, warningDetailFragment,"warningDetailFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("warningDetailFragment");
			break;

		default:
			
			break;
		}
		
	}
	
	private void clearSelection() {
		tvCalibration.setTextColor(Color.parseColor("#DBDBDB"));
		tvMaintance.setTextColor(Color.parseColor("#DBDBDB"));
		tvMeasure.setTextColor(Color.parseColor("#DBDBDB"));
		tvWarring.setTextColor(Color.parseColor("#DBDBDB"));
		measureDetail.setBackgroundColor(Color.parseColor("#1296DB"));
		calibrationDetail.setBackgroundColor(Color.parseColor("#1296DB"));
		maintenanceDetail.setBackgroundColor(Color.parseColor("#1296DB"));
		warningDetail.setBackgroundColor(Color.parseColor("#1296DB"));
	}
	
	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		Log.d(tag, "onPuse");
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		Log.d(tag, "onDestroy");
	}
	
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		Log.d(tag, "onResume");
		super.onResume();
	}
	
	@Override
	public void onDetach() {
		// TODO 自动生成的方法存根
		Log.d(tag, "onDetach");
		super.onDetach();
	}
	
	@Override
	public void onStop() {
		// TODO 自动生成的方法存根
		Log.d(tag, "onStop");
		super.onStop();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO 自动生成的方法存根
		super.onHiddenChanged(hidden);
		
		if (!hidden) {
			//Log.d(tag, "this = "+String.valueOf(isVisible()));
			lastFragment = fragmentManager.findFragmentById(R.id.details_layout);
			if (lastFragment != null) {
				
				//Log.d(tag,"lastFragment"+String.valueOf(lastFragment.isVisible())+";"+String.valueOf(lastFragment.isAdded())+";"+lastFragment.getClass().getName());
			}
			else {
				//Log.d(tag, "null");
				setTabSelection(0);
			}
			/*if (!isVisible()) {
				//Log.d(tag, "this = "+String.valueOf(isAdded()));
				//getActivity().getFragmentManager().beginTransaction().add(R.id.content, this).commit();
			}*/
		}
		else {
			//Log.d(tag, "hidden");
		}
	}
}
