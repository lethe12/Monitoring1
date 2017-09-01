package cn.com.grean.monitoring;

import java.lang.reflect.Field;

import cn.com.grean.Presenter.LogFragmentManager;
import cn.com.grean.Presenter.LogFragmentPresenter;
import cn.com.grean.model.SettingModel;
import cn.com.grean.model.SettingModelImp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogFragment extends Fragment implements OnClickListener{
	private final static String tag = "LogFragment";
	private View LogView;
	private View HistoryView;
	private View exportView;
	private View deleteView;
	private TextView tvData;
	private TextView tvLog;
	private TextView tvExport;
	private TextView tvDelete;
	
	private ProgressDialog pd;  
	
	private LogDetailFragment logDetailFragment;
	private HistoryFragment historyFragment;
	
	//private FragmentManager fragmentManager; 
	private FragmentManager fragmentManager;
	private LogFragmentPresenter presenter;
	
	private Fragment lastFragment;
	private SettingModel model;

	public LogFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View messageLayout = inflater.inflate(R.layout.activity_log, container, false);
		initView(messageLayout);
		//fragmentManager = getFragmentManager();
		fragmentManager = getActivity().getFragmentManager();  
		presenter = new LogFragmentManager();
        setTabSelection(0);
		model=new SettingModelImp();
		return messageLayout;
	}
	
	void initView(View v){
		HistoryView = v.findViewById(R.id.log_menu_one);
		LogView = v.findViewById(R.id.log_menu_two);
		exportView = v.findViewById(R.id.log_menu_three);
		deleteView = v.findViewById(R.id.log_menu_fourth);
		LogView.setOnClickListener(this);
		HistoryView.setOnClickListener(this);
		exportView.setOnClickListener(this);
		deleteView.setOnClickListener(this);
		tvData=(TextView) v.findViewById(R.id.tvLogData);
		tvDelete=(TextView) v.findViewById(R.id.tvLogDelete);
		tvExport=(TextView) v.findViewById(R.id.tvLogExport);
		tvLog=(TextView) v.findViewById(R.id.tvLogLog);
	}
	
	private void clearSelection() {
		tvData.setTextColor(Color.parseColor("#dbdbdb"));
		tvDelete.setTextColor(Color.parseColor("#dbdbdb"));
		tvExport.setTextColor(Color.parseColor("#dbdbdb"));
		tvLog.setTextColor(Color.parseColor("#dbdbdb"));
		LogView.setBackgroundColor(Color.parseColor("#1296DB"));
		HistoryView.setBackgroundColor(Color.parseColor("#1296DB"));
		exportView.setBackgroundColor(Color.parseColor("#1296DB"));
		deleteView.setBackgroundColor(Color.parseColor("#1296DB"));
	}

	@Override
	public void onClick(View v) {
		
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.log_menu_one:
			setTabSelection(0);
			break;
		case R.id.log_menu_two:
			setTabSelection(1);
			break;
		case R.id.log_menu_three:
			RunLogAndData run = new RunLogAndData();
			run.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "ExportAllData");
			break;
		case R.id.log_menu_fourth:
			final EditText inputServer = new EditText(getActivity());
			inputServer.setInputType(0x81);
			new AlertDialog.Builder(getActivity()).setTitle("请输入管理员密码")
	         .setIcon(android.R.drawable.ic_dialog_info)
		     .setView(inputServer)
		     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (model.isPassWordRight(inputServer.getText().toString())) {
							RunLogAndData run = new RunLogAndData();
							run = new RunLogAndData();
							run.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "DeleteAllData");
							
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
			tvData.setTextColor(Color.parseColor("#1296DB"));
			HistoryView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if(historyFragment == null){
				historyFragment = new HistoryFragment();
			}
			//transaction.attach(historyFragment).commit();
			transaction.replace(R.id.log_details_layout, historyFragment,"historyFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("historyFragment");
			break;
		case 1:
			tvLog.setTextColor(Color.parseColor("#1296DB"));
			LogView.setBackgroundColor(Color.parseColor("#DBDBDB"));
			if (logDetailFragment == null) {
				logDetailFragment = new LogDetailFragment();
			}
			//transaction.attach(logDetailFragment).commit();
			transaction.replace(R.id.log_details_layout, logDetailFragment,"logDetailFragment").commit();
			lastFragment = fragmentManager.findFragmentByTag("logDetailFragment");
			break;
		default:
			break;
		}		
		
	}
	
	class RunLogAndData extends AsyncTask<String, Void, String>{
		
		@Override
		protected void onPreExecute() {
			// TODO 自动生成的方法存根
			super.onPreExecute();
			pd= new ProgressDialog(getActivity());
			pd.setMax(100);
			pd.setTitle("提示");
			pd.setMessage("数据处理中，其稍候..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO 自动生成的方法存根
			if (params[0].equals("DeleteAllData")) {
				presenter.DeleteAllData();
				return "删除成功";
			}
			else if (params[0].equals("ExportAllData")) {
				
				if (presenter.ExportAllData()) {
					return "导出成功";
				}
				else {
					return "导出失败";
				}
				
			}
			else {
				
				return "系统异常";
			}
			
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			pd.dismiss();
			setTabSelection(0);
			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO 自动生成的方法存根
		super.onHiddenChanged(hidden);
		if (!hidden) {
			//Log.d(tag, "this = "+String.valueOf(isVisible()));
			lastFragment = fragmentManager.findFragmentById(R.id.log_details_layout);
			if (lastFragment != null) {
				
				//Log.d(tag,"lastFragment"+String.valueOf(lastFragment.isVisible())+";"+String.valueOf(lastFragment.isAdded())+";"+lastFragment.getClass().getName());
			}
			else {
				//Log.d(tag, "null");
				setTabSelection(0);
			}
			/*if (!isVisible()) {
				//Log.d(tag, "this = "+String.valueOf(isAdded()));
				getActivity().getFragmentManager().beginTransaction().add(R.id.content, this).commit();
			}*/
			
		}
		else {
			//Log.d(tag, "hidden");
		}
	}


}
