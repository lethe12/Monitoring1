package cn.com.grean.monitoring;

import cn.com.grean.model.SettingModel;
import cn.com.grean.model.SettingModelImp;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment implements android.view.View.OnClickListener{
	private final static String tag = "SettingFragment";
	private View communicationView;
	private View calculateView;
	private View systemView;
	private View advancedView;
	private TextView tvCommunication;
	private TextView tvCalculate;
	private TextView tvSystem;
	private TextView tvAdvance;
	
	private CommunicationFragment communicationFragment;
	private CalculateFragment calculateFragment;
	private SystemFragment systemFragment;
	private AdvancedFragment advancedFragment;
	
	private FragmentManager fragmentManager; 
	private Fragment lastFragment;
	private SettingModel model;

	public SettingFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View messageLayout = inflater.inflate(R.layout.activity_setting, container, false);
		initView(messageLayout);
		fragmentManager = getFragmentManager();  
        setTabSelection(0);
        model = new SettingModelImp();
		return messageLayout;
	}
	
	void initView(View v){
		communicationView = v.findViewById(R.id.setting_menu_one);
		calculateView = v.findViewById(R.id.setting_menu_two);
		systemView = v.findViewById(R.id.setting_menu_three);
		advancedView = v.findViewById(R.id.setting_menu_fourth);
		communicationView.setOnClickListener(this);
		calculateView.setOnClickListener(this);
		systemView.setOnClickListener(this);
		advancedView.setOnClickListener(this);
		tvAdvance = (TextView) v.findViewById(R.id.tvSettingAdvance);
		tvCalculate = (TextView) v.findViewById(R.id.tvSettingCalculate);
		tvCommunication = (TextView) v.findViewById(R.id.tvSettingCommunication);
		tvSystem = (TextView) v.findViewById(R.id.tvSettingSystem);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.setting_menu_one:
			setTabSelection(0);
			break;
		case R.id.setting_menu_two:
			setTabSelection(1);
			break;
		case R.id.setting_menu_three:
			setTabSelection(2);
			break;
		case R.id.setting_menu_fourth:
			
			final EditText inputServer = new EditText(getActivity());
			inputServer.setInputType(0x81);
			new AlertDialog.Builder(getActivity()).setTitle("请输入管理员密码")
	         .setIcon(android.R.drawable.ic_dialog_info)
		     .setView(inputServer)
		     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (model.isPassWordRight(inputServer.getText().toString())) {
							setTabSelection(3);
							
						} else {
							Toast.makeText(getActivity(), "密码错误",Toast.LENGTH_SHORT).show();
						}
						
					}
				})
		     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();		
			
			break;

		default:
			break;
		}
	}
	
	private void setTabSelection(int index){
		clearSelection();
		FragmentTransaction transaction = fragmentManager.beginTransaction();  
		switch (index) {
		case 0:
			tvCommunication.setTextColor(Color.parseColor("#1296DB"));
			communicationView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (communicationFragment == null) {
				communicationFragment = new CommunicationFragment();
			}
			//transaction.attach(communicationFragment);
			transaction.replace(R.id.setting_details_layout, communicationFragment,"communicationFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("communicationFragment");
			break;
		case 1:
			tvCalculate.setTextColor(Color.parseColor("#1296DB"));
			calculateView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (calculateFragment == null) {
				calculateFragment = new CalculateFragment();
			}
			//transaction.attach(calculateFragment);
			transaction.replace(R.id.setting_details_layout, calculateFragment,"lastFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("lastFragment");
			break;
		case 2:
			tvSystem.setTextColor(Color.parseColor("#1296DB"));
			systemView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (systemFragment == null) {
				systemFragment = new SystemFragment();
			}
			//transaction.attach(systemFragment);
			transaction.replace(R.id.setting_details_layout, systemFragment,"systemFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("systemFragment");
			break;
		case 3:
			tvAdvance.setTextColor(Color.parseColor("#1296DB"));
			advancedView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (advancedFragment == null) {
				advancedFragment = new AdvancedFragment();
			}
			//transaction.attach(advancedFragment);
			transaction.replace(R.id.setting_details_layout, advancedFragment,"advancedFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("advancedFragment");
			break;

		default:
			break;
		}
		
		
	}
	private void clearSelection() {
		tvAdvance.setTextColor(Color.parseColor("#dbdbdb"));
		tvCalculate.setTextColor(Color.parseColor("#dbdbdb"));
		tvCommunication.setTextColor(Color.parseColor("#dbdbdb"));
		tvSystem.setTextColor(Color.parseColor("#dbdbdb"));
		advancedView.setBackgroundColor(Color.parseColor("#1296DB"));
		calculateView.setBackgroundColor(Color.parseColor("#1296DB"));
		communicationView.setBackgroundColor(Color.parseColor("#1296DB"));
		systemView.setBackgroundColor(Color.parseColor("#1296DB"));
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO 自动生成的方法存根
		super.onHiddenChanged(hidden);
		/*if (!hidden) {
			Log.d(tag, lastFragment.getClass().getName()+String.valueOf(lastFragment.isVisible()));
			fragmentManager.beginTransaction().replace(R.id.setting_details_layout, lastFragment, lastFragment.getClass().getName()).commit();
		}*/
		//if (!lastFragment.isVisible()) {
		//}
		if (!hidden) {
			//Log.d(tag, "this = "+String.valueOf(isVisible()));
			lastFragment = fragmentManager.findFragmentById(R.id.setting_details_layout);
			if (lastFragment!=null) {
				
				//Log.d(tag,"lastFragment"+String.valueOf(lastFragment.isVisible())+";"+String.valueOf(lastFragment.isAdded())+";"+lastFragment.getClass().getName());
			}
			else {
				//Log.d(tag, "null");
				setTabSelection(0);
			}
			if (!isVisible()) {
				//Log.d(tag, "this = "+String.valueOf(isAdded()));
				getActivity().getFragmentManager().beginTransaction().add(R.id.content, this).commit();
			}
		}
		else {
			//Log.d(tag, "hidden");
		}
	}

}
