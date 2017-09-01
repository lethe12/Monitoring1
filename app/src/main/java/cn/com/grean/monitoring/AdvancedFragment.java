package cn.com.grean.monitoring;

import java.lang.reflect.Field;

import cn.com.grean.Presenter.AdvanceDetail;
import cn.com.grean.Presenter.DetailAdvancedPresenter;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

public class AdvancedFragment extends Fragment implements AdvancedFragmentView,OnClickListener,OnItemSelectedListener{
	@SuppressWarnings("unused")
	private final static String tag = "AdvancedFragment";
	private Spinner spScriptNames;
	private Button btnLoadScript;
	private Button btnViewScript;
	private Spinner spDevicesNames;
	private Spinner spCommunicationProtocol;
	private Spinner spVirtualDevicesNames;
	private Spinner spDevicesRanges;
	private EditText etVirtualParams;
	private Button btnSaveVirtualDevicesParams;
	private Button btnEnableVirtualDevices;
	private EditText [] etCalParams = new EditText [4];
	private Button btnSaveCalParams;
	private Button btnChangePassWord;
	private ListView lvScriptContent;
	private DetailAdvancedPresenter presenter;
	private int scriptPos = 0;
	private int virtualDevicesPos = 0;
	private int devicesRange =0 ;
	private EditText etUpdataUri;
	private Button btnSaveURI;
	
	
	public AdvancedFragment() {
		// TODO 自动生成的构造函数存根
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_advanced, container, false);
		initView(view);
		presenter = new AdvanceDetail(this);
		presenter.showDetail();
		return view;
	}
	
	void initView(View v){
		spScriptNames = (Spinner) v.findViewById(R.id.spAdvanceScriptNames);
		spDevicesRanges = (Spinner) v.findViewById(R.id.spAdvanceChooseRanges);
		btnLoadScript = (Button) v.findViewById(R.id.btnAdvanceLoadScript);
		btnViewScript = (Button) v.findViewById(R.id.btnAdvanceViewScript);
		spDevicesNames = (Spinner) v.findViewById(R.id.spAdvanceChooseDevice);
		spCommunicationProtocol = (Spinner) v.findViewById(R.id.spAdvanceChooseProtocol);
		spVirtualDevicesNames = (Spinner) v.findViewById(R.id.spVirualDevicesParams);
		etVirtualParams = (EditText) v.findViewById(R.id.etVritualDevicesParams);
		btnSaveVirtualDevicesParams = (Button) v.findViewById(R.id.btnSaveVirtualDevicesParams);
		btnEnableVirtualDevices = (Button) v.findViewById(R.id.btnEnableVirtualDevices);
		etCalParams[0] = (EditText) v.findViewById(R.id.etAdvanceSlopeFloor);
		etCalParams[1] = (EditText) v.findViewById(R.id.etAdvanceSlopeUpper);
		etCalParams[2] = (EditText) v.findViewById(R.id.etAdvanceInterceptFloor);
		etCalParams[3] = (EditText) v.findViewById(R.id.etAdvanceinterceptUpper);
		btnSaveCalParams = (Button) v.findViewById(R.id.btnAdvanceSaveCalParams);
		btnChangePassWord = (Button) v.findViewById(R.id.btnAdvancesChangePassWord);
		lvScriptContent = (ListView) v.findViewById(R.id.lvScriptContent);
		etUpdataUri = (EditText) v.findViewById(R.id.etAdvanceUpdataUri);
		btnSaveURI = (Button) v.findViewById(R.id.btnAdvancesSaveUri);
		
		btnLoadScript.setOnClickListener(this);
		btnViewScript.setOnClickListener(this);
		btnSaveVirtualDevicesParams.setOnClickListener(this);
		btnEnableVirtualDevices.setOnClickListener(this);
		btnSaveCalParams.setOnClickListener(this);
		btnChangePassWord.setOnClickListener(this);
		btnSaveURI.setOnClickListener(this);
	}
	


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "监听");
		switch (arg0.getId()) {
		case R.id.spAdvanceChooseDevice:
			presenter.setChooseDevices(arg2);
			//Toast.makeText(getActivity(), "参数设置成功，重启生效", Toast.LENGTH_SHORT).show();
			break;
		case R.id.spAdvanceChooseProtocol:
			presenter.setChooseProtocol(arg2);
			//Toast.makeText(getActivity(), "协议设置成功，重启生效", Toast.LENGTH_SHORT).show();
			break;
		case R.id.spAdvanceScriptNames:
			scriptPos = arg2;
			break;
		case R.id.spVirualDevicesParams:
			virtualDevicesPos = arg2;
			etVirtualParams.setText(presenter.getVirtualDevices(arg2));
			break;
		case R.id.spAdvanceChooseRanges:
			this.devicesRange = arg2;
			presenter.setDevicesRange(arg2);
			break;
		default:
			break;
		}
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO 自动生成的方法存根
		
	}


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnAdvancesSaveUri:
			presenter.saveUpdataURI(etUpdataUri.getText().toString());
			break;
		case R.id.btnAdvanceLoadScript:
			LoadScript loadScript = new LoadScript();
			loadScript.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, scriptPos,devicesRange);
			break;
		case R.id.btnAdvanceSaveCalParams:
			float [] params = new float[4];
			for (int i = 0; i < 4; i++) {				
				params[i] = Float.valueOf(etCalParams[i].getText().toString());
				Log.d(tag, String.valueOf(params[i]));
			}
			if (presenter.saveCalParams(params)) {
				Toast.makeText(getActivity(), "保存成功，重启生效", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "参数设置有误，请重新设置", Toast.LENGTH_SHORT).show();
			}
			
			break;
		case R.id.btnAdvancesChangePassWord:
			final EditText inputServer = new EditText(getActivity());
			inputServer.setInputType(0x81);
			new AlertDialog.Builder(getActivity()).setTitle("请输入新密码")
	         .setIcon(android.R.drawable.ic_dialog_info)
		     .setView(inputServer)
		     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						presenter.savePassword(inputServer.getText().toString());
						Toast.makeText(getActivity(), "修改成功",Toast.LENGTH_SHORT).show();							
					}
				})
		     .setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();	
			break;
		case R.id.btnAdvanceViewScript:
			Log.d(tag, String.valueOf(scriptPos));
			presenter.interviewScript(scriptPos);
			break;
		case R.id.btnSaveVirtualDevicesParams:
			presenter.setVirtualDevices(virtualDevicesPos, etVirtualParams.getText().toString());
			break;
		case R.id.btnEnableVirtualDevices:
			presenter.enableVirtualDevices();
			break;

		default:
			break;
		}
	}
	
	class LoadScript extends AsyncTask<Integer, Void, Boolean>{
		private ProgressDialog pd; 
		@Override
		protected void onPreExecute() {
			// TODO 自动生成的方法存根
			super.onPreExecute();
			pd= new ProgressDialog(getActivity());
			pd.setMax(100);
			pd.setTitle("提示");
			pd.setMessage("脚本处理中，其稍候..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO 自动生成的方法存根
			super.onPostExecute(result);
			pd.dismiss();
			if (result) {
				Toast.makeText(getActivity(), "导入成功", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "未找到脚本文件", Toast.LENGTH_SHORT).show();
			}
		}
		@Override
		protected Boolean doInBackground(Integer... params) {
			// TODO 自动生成的方法存根
			if(presenter.loadScript(params[0],params[1])){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return true;
			}
			else {
				return false;
			}

		}
		
	}
	
	@Override
	public void showDetail(float[] params, int devicesName, int communicationProtocol,int devicesRange,String uri) {
		// TODO 自动生成的方法存根
		if (params.length == 4) {
			for (int i = 0; i < 4; i++) {
				etCalParams[i].setText(String.valueOf(params[i]));				
			}
		}
		
		ArrayAdapter<String> devicesNameAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spnner, presenter.getDeviceNames());
		spDevicesNames.setAdapter(devicesNameAdapter);
		spDevicesNames.setOnItemSelectedListener(this);
		spDevicesNames.setSelection(devicesName);
		
		ArrayAdapter<String> communicationProtocolAdapter = new ArrayAdapter<String>(getActivity(),R.layout.my_spnner, presenter.getCommunicationProtocolNames());
		spCommunicationProtocol.setAdapter(communicationProtocolAdapter);
		spCommunicationProtocol.setOnItemSelectedListener(this);
		spCommunicationProtocol.setSelection(communicationProtocol);
		
		ArrayAdapter<String> scriptAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spnner, presenter.getScriptNames());
		spScriptNames.setAdapter(scriptAdapter);
		spScriptNames.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> virtualDevicesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spnner, presenter.getVirtualDevicesNames());
		spVirtualDevicesNames.setAdapter(virtualDevicesAdapter);
		spVirtualDevicesNames.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> devicseRangesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.my_spnner, presenter.getDevicesRanges());
		spDevicesRanges.setAdapter(devicseRangesAdapter);
		spDevicesRanges.setOnItemSelectedListener(this);
		spDevicesRanges.setSelection(devicesRange);
		this.devicesRange = devicesRange;
		
		etVirtualParams.setText(presenter.getVirtualDevices(0));
		etUpdataUri.setText(uri);
	}


	@Override
	public void showScriptContent(String[] list) {
		// TODO 自动生成的方法存根
		Log.d(tag, "显示脚本");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.script_listview, R.id.text1, list);
		//final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.script_listview,list);
		lvScriptContent.setAdapter(adapter);
	}
	
	
}
