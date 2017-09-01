package cn.com.grean.monitoring;

import cn.com.grean.RS485MsgListener;
import cn.com.grean.Presenter.CommunicationDetail;
import cn.com.grean.Presenter.DetailCommunicationPresenter;
import cn.com.grean.model.CommunicationInfo;
import cn.com.grean.protocol.ProtocolProcessorImp;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class CommunicationFragment extends Fragment implements CommunicationFragmentView,OnClickListener,OnItemSelectedListener,RS485MsgListener{
	private final static String tag = "CommunicationFragment";
	private final static int MSG_ONLINE = 1;
	private final static int MSG_OFFLINE = 2;
	private ToggleButton tbSlaveMode; 
	private Spinner spSlaveBaudRate;
	private EditText etSlaveAddress;
	private View linearLayoutSlaveInfo;
	private View linearLayoutTCP;
	private Button btnSaveSlaveMode;
	private EditText etTestTarget;
	private Button btnSaveTestCmd;
	private ToggleButton tbTestMode;
	
	
	private TextView tvReceiveCmd;
	private TextView tvTestCmd;
	private EditText etIP;
	private EditText etPort;
	private ToggleButton tbEnableTCP;
	private TextView tvIsConected;
	private EditText etTCPID;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_OFFLINE:
				tvIsConected.setText("未连接");
				break;
			case MSG_ONLINE:
				tvIsConected.setText("已连接");
				break;

			default:
				break;
			}
			
		};
	};
	private DetailCommunicationPresenter presenter;
	public CommunicationFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_communication, container, false);
		initView(view);
		presenter = new CommunicationDetail(this);
		presenter.showDetail();
		IntentFilter filter_dynamic = new IntentFilter();  
        filter_dynamic.addAction("RS485Context");  //更新下次测量时间
        getActivity().registerReceiver(dynamicReceiver, filter_dynamic);
        ProtocolProcessorImp.getInstance().setMsgListener(this);
		//Log.d(tag, "live");
		return view;
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		Log.d(tag, "重置通讯测试");
		
		presenter.returnDefault();
		getActivity().unregisterReceiver(dynamicReceiver);
		//RS485.getInstance().setMsgListener(null);
		super.onDestroy();
	}
	
	void initView (View v){
		tbSlaveMode = (ToggleButton) v.findViewById(R.id.tbSettingMasterMode);
		spSlaveBaudRate = (Spinner) v.findViewById(R.id.spSettingSlaveBaudRate);
		etSlaveAddress = (EditText) v.findViewById(R.id.etSettingSlaveAddress);
		linearLayoutSlaveInfo = v.findViewById(R.id.linearLayoutSettingSlaveMode);
		linearLayoutTCP = v.findViewById(R.id.viewCommunicationTCP);
		btnSaveSlaveMode = (Button) v.findViewById(R.id.btnSettingSaveSlaveMode);
		etTestTarget = (EditText) v.findViewById(R.id.etSettingTest);
		btnSaveTestCmd = (Button) v.findViewById(R.id.btnSettingSaveTest);
		tbTestMode = (ToggleButton) v.findViewById(R.id.tbSettingTestMode);
		tvReceiveCmd = (TextView) v.findViewById(R.id.tvSettingReceiveCmd);
		tvTestCmd = (TextView) v.findViewById(R.id.tvSettingTestCmd);
		etIP = (EditText) v.findViewById(R.id.etCommunicationIP);
		etPort = (EditText) v.findViewById(R.id.etCommunicationPort);
		tbEnableTCP = (ToggleButton) v.findViewById(R.id.tbCommunicationRun);
		tvIsConected = (TextView) v.findViewById(R.id.tvCommunicationConected);
		etTCPID = (EditText) v.findViewById(R.id.etCommunicationID);
		tbSlaveMode.setOnClickListener(this);
		btnSaveSlaveMode.setOnClickListener(this);
		btnSaveTestCmd.setOnClickListener(this);
		tbEnableTCP.setOnClickListener(this);
		tbTestMode.setOnClickListener(this);
	}

	@Override
	public void showCommunicationInfo(CommunicationInfo info) {
		// TODO 自动生成的方法存根
		if (info.isMasterMode()) {			
			linearLayoutSlaveInfo.setVisibility(View.GONE);
			linearLayoutTCP.setVisibility(View.VISIBLE);
		}
		else {
			linearLayoutSlaveInfo.setVisibility(View.VISIBLE);
			linearLayoutTCP.setVisibility(View.GONE);
		}
		tbSlaveMode.setChecked(info.isMasterMode());
		tbEnableTCP.setChecked(presenter.isTCPConnected());
		etTCPID.setText(info.getTcpId());
		etIP.setText(info.getTcpIp());
		etPort.setText(String.valueOf(info.getTcpPort()));
		if (presenter.isTCPConnected()) {
			tvIsConected.setText("已连接");
			etPort.setEnabled(false);
			etIP.setEnabled(false);
			etTCPID.setEnabled(false);
		}
		else {
			tvIsConected.setText("未连接");
			etPort.setEnabled(true);
			etIP.setEnabled(true);
			etTCPID.setEnabled(true);
		}
		etSlaveAddress.setText(String.valueOf(info.getSlaveAddress()));

		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, presenter.getSlaveBaudRate());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.system_spnner, presenter.getSlaveBaudRate());
		spSlaveBaudRate.setAdapter(adapter);
		spSlaveBaudRate.setOnItemSelectedListener(this);
		spSlaveBaudRate.setSelection(presenter.getDefaultSlaveBaudRate());
		//spSlaveBaudRate.setSelection(2, true);
	}


	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btnSettingSaveSlaveMode:
			//Log.d(tag, etSlaveAddress.getText().toString());
			if (presenter.setSlveAddress(Integer.valueOf(etSlaveAddress.getText().toString()))) {
				Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "地址超范围，请重新设置", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnSettingSaveTest:
			//presenter.setDefaultValue(Float.valueOf(etTestTarget.getText().toString()));
			break;
		case R.id.tbSettingTestMode:
			if (tbTestMode.isChecked()) {
				presenter.setDefaultValue(Float.valueOf(etTestTarget.getText().toString()));
				Toast.makeText(getActivity(), "开启通讯测试模式", Toast.LENGTH_SHORT).show();
			}else {
				presenter.returnDefault();
				Toast.makeText(getActivity(), "关闭通讯测试模式", Toast.LENGTH_SHORT).show();
			}			
			break;
		case R.id.tbSettingMasterMode:
			presenter.setCommunicationMode(tbSlaveMode.isChecked());
			break;
		case R.id.tbCommunicationRun:
			presenter.setTCPConnect(etIP.getText().toString(),Integer.valueOf(etPort.getText().toString()),etTCPID.getText().toString(), tbEnableTCP.isChecked());
			if (tbEnableTCP.isChecked()) {
				etIP.setEnabled(false);
				etPort.setEnabled(false);
				etTCPID.setEnabled(false);
			}
			else {
				etIP.setEnabled(true);
				etPort.setEnabled(true);
				etTCPID.setEnabled(true);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		presenter.setSlaveBaudRate(arg2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void updataTestCmd(String text) {
		// TODO 自动生成的方法存根
		tvTestCmd.setText(text);
	}

	@Override
	public void updataCommunicationMode(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			linearLayoutSlaveInfo.setVisibility(View.GONE);
			linearLayoutTCP.setVisibility(View.VISIBLE);
		}
		else {
			linearLayoutSlaveInfo.setVisibility(View.VISIBLE);
			linearLayoutTCP.setVisibility(View.GONE);
		}
	}

	@Override
	public void onReceiveOneMsg(String text) {
		// TODO 自动生成的方法存根
		//Log.d(tag, text);
		Intent intent = new Intent();
		intent.setAction("RS485Context");
		intent.putExtra("text", text);
		getActivity().sendBroadcast(intent);
	}
	
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {
		private int times= 0;
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			if (intent.getAction().equals("RS485Context")) {
				if (times > 2) {
					times = 0;
					tvReceiveCmd.setText("接收区:\n");
				}
				else {
					times++;
				}
				String text = intent.getStringExtra("text")+"\n";
				tvReceiveCmd.append(text);
			}
		}
		
	};
	
	public void onDestroyView() {
		tbTestMode.setChecked(false);
		super.onDestroyView();
		Log.d(tag, "onDestroyView");
	}
	
	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		tbTestMode.setChecked(false);
		super.onPause();
	}
	
	@Override
	public void showTCPConnect(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			handler.sendEmptyMessage(MSG_ONLINE);
		}
		else {
			handler.sendEmptyMessage(MSG_OFFLINE);
		}
		Intent intent = new Intent();
		intent.setAction("connectTCP");
		intent.putExtra("key", key);
		getActivity().sendBroadcast(intent);	
	};

}
