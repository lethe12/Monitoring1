package cn.com.grean.monitoring;

import cn.com.grean.EquipmentData;
import cn.com.grean.InjectionPump.InjectionPumpManipulator;
import cn.com.grean.InjectionPump.InjectionPumpManipulatorListener;
import cn.com.grean.Presenter.DetailMaintenancePresenter;
import cn.com.grean.Presenter.MaintenanceDetail;
import cn.com.grean.RobotArm.RobotArmManipulator;
import cn.com.grean.RobotArm.RobotArmManipulatorListener;
import cn.com.grean.script.ScriptRun;
import cn.com.grean.script.instruction.DevicesData;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MaintenanceDetailFragment extends Fragment implements MaintenanceDetailView,OnClickListener,RobotArmManipulatorListener,InjectionPumpManipulatorListener{
	private final static String tag = "MaintenanceDetail";
	private Button btnInit;
	private Button btnClear;
	private ToggleButton tbChangMaintenanceModel;
	private View detailMaintenance;
	private TextView tvDectectInfo;
	private EditText [] etTempParams = new EditText[2];
	private Button [] btnCalTemp = new Button[2];
	private Button btnGetDectectInfo;
	private Button btnGetDevicesInfo;
	private ToggleButton [] tbVDSwtich = new ToggleButton[5];
	private ToggleButton [] tbValveSwich = new ToggleButton[28];
	private EditText [] etPumpRound = new EditText[4];
	private EditText [] etPumpTime = new EditText[4];
	private Button [] btnPumpStop = new Button[4];
	private Button [] btnPumpStart = new Button[4];
	private EditText [] etPumpParams = new EditText[4];
	private Button [] btnPumpParams = new Button[4];
	private View [] pumpView = new View[4];
	private Switch swRobotArmScan;
	private TextView tvRobotArmPos;

	private DetailMaintenancePresenter presenter;
	private RobotArmManipulator robotArm;
	private TextView[] tvPose = new TextView[20];
	private EditText etPoseNum;
	private Button btnZeroPose,btnSetZeroPose,btnSavePose,test1,test2;
	private ClickPose clickPose;

	private Button btnInjectionPumpInit,btnInjectionPumpMove;
	private EditText etInjectionPumpMove;
	private Switch swInjectionPumpValve;
    private InjectionPumpManipulator injectionPump;
    private View injectionPumpView,robotArmView;

	private String robotArmStateString,PoseString;
	private int showPoseNum;
	private static final int msgShowRobotState = 1;
	private static final int msgShowPose = 2;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case msgShowRobotState:
					tvRobotArmPos.setText(robotArmStateString);
					break;
				case msgShowPose:
					tvPose[showPoseNum].setText(PoseString);
					break;
				default:
					break;

			}
		}
	};
	
	public MaintenanceDetailFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
		initView(view);
		clickPose = new ClickPose(tvPose,view);
		presenter = new MaintenanceDetail(this);
		presenter.showInfo();
		robotArm = new RobotArmManipulator(this);
		swRobotArmScan.setChecked(robotArm.isScanRun());
		clickPose.showPoses(robotArm.getLocalPoses());
        injectionPump = new InjectionPumpManipulator(this);
		return view;
	}
	
	void initView(View v){
		btnInit=(Button) v.findViewById(R.id.btnOperateInit);
		btnClear=(Button) v.findViewById(R.id.btnOperateClear);
		tbChangMaintenanceModel=(ToggleButton) v.findViewById(R.id.tbOperateMaintenModel);
		tvDectectInfo = (TextView) v.findViewById(R.id.tvOperateDectector);
		detailMaintenance = v.findViewById(R.id.scrollViewDetailMaintenance);
		etTempParams[0] = (EditText) v.findViewById(R.id.etOperateTempParam1);
		etTempParams[1] = (EditText) v.findViewById(R.id.etOperateTempParam2);
		btnCalTemp[0] = (Button) v.findViewById(R.id.btnTempParam1);
		btnCalTemp[1] = (Button) v.findViewById(R.id.btnTempParam2);
		btnGetDectectInfo = (Button) v.findViewById(R.id.btnGetDetector);
		btnGetDevicesInfo = (Button) v.findViewById(R.id.btnGetDevices);
		tbVDSwtich[0] = (ToggleButton) v.findViewById(R.id.tbOperateVD1);
		tbVDSwtich[1] = (ToggleButton) v.findViewById(R.id.tbOperateVD2);
		tbVDSwtich[2] = (ToggleButton) v.findViewById(R.id.tbOperateVD3);
		tbVDSwtich[3] = (ToggleButton) v.findViewById(R.id.tbOperateVD4);
		tbVDSwtich[4] = (ToggleButton) v.findViewById(R.id.tbOperateVD5);
		tbValveSwich[0] = (ToggleButton) v.findViewById(R.id.tbOperateV1);
		tbValveSwich[1] = (ToggleButton) v.findViewById(R.id.tbOperateV2);
		tbValveSwich[2] = (ToggleButton) v.findViewById(R.id.tbOperateV3);
		tbValveSwich[3] = (ToggleButton) v.findViewById(R.id.tbOperateV4);
		tbValveSwich[4] = (ToggleButton) v.findViewById(R.id.tbOperateV5);
		tbValveSwich[5] = (ToggleButton) v.findViewById(R.id.tbOperateV6);
		tbValveSwich[6] = (ToggleButton) v.findViewById(R.id.tbOperateV7);
		tbValveSwich[7] = (ToggleButton) v.findViewById(R.id.tbOperateV8);
		tbValveSwich[8] = (ToggleButton) v.findViewById(R.id.tbOperateV9);
		tbValveSwich[9] = (ToggleButton) v.findViewById(R.id.tbOperateV10);
		tbValveSwich[10] = (ToggleButton) v.findViewById(R.id.tbOperateV11);
		tbValveSwich[11] = (ToggleButton) v.findViewById(R.id.tbOperateV12);
		tbValveSwich[12] = (ToggleButton) v.findViewById(R.id.tbOperateV13);
		tbValveSwich[13] = (ToggleButton) v.findViewById(R.id.tbOperateV14);
		tbValveSwich[14] = (ToggleButton) v.findViewById(R.id.tbOperateV15);
		tbValveSwich[15] = (ToggleButton) v.findViewById(R.id.tbOperateV16);
		tbValveSwich[16] = (ToggleButton) v.findViewById(R.id.tbOperateV17);
		tbValveSwich[17] = (ToggleButton) v.findViewById(R.id.tbOperateV18);
		tbValveSwich[18] = (ToggleButton) v.findViewById(R.id.tbOperateV19);
		tbValveSwich[19] = (ToggleButton) v.findViewById(R.id.tbOperateV20);
		tbValveSwich[20] = (ToggleButton) v.findViewById(R.id.tbOperateV21);
		tbValveSwich[21] = (ToggleButton) v.findViewById(R.id.tbOperateV22);
		tbValveSwich[22] = (ToggleButton) v.findViewById(R.id.tbOperateV23);
		tbValveSwich[23] = (ToggleButton) v.findViewById(R.id.tbOperateV24);
		tbValveSwich[24] = (ToggleButton) v.findViewById(R.id.tbOperateV25);
		tbValveSwich[25] = (ToggleButton) v.findViewById(R.id.tbOperateV26);
		tbValveSwich[26] = (ToggleButton) v.findViewById(R.id.tbOperateV27);
		tbValveSwich[27] = (ToggleButton) v.findViewById(R.id.tbOperateV28);
		etPumpParams[0] = (EditText) v.findViewById(R.id.etOperatePumpParam1);
		etPumpParams[1] = (EditText) v.findViewById(R.id.etOperatePumpParam2);
		etPumpParams[2] = (EditText) v.findViewById(R.id.etOperatePumpParam3);
		etPumpParams[3] = (EditText) v.findViewById(R.id.etOperatePumpParam4);
		etPumpRound[0] = (EditText) v.findViewById(R.id.etOperatePumpRound1);
		etPumpRound[1] = (EditText) v.findViewById(R.id.etOperatePumpRound2);
		etPumpRound[2] = (EditText) v.findViewById(R.id.etOperatePumpRound3);
		etPumpRound[3] = (EditText) v.findViewById(R.id.etOperatePumpRound4);
		etPumpTime[0] = (EditText) v.findViewById(R.id.etOperatePumpTime1);
		etPumpTime[1] = (EditText) v.findViewById(R.id.etOperatePumpTime2);
		etPumpTime[2] = (EditText) v.findViewById(R.id.etOperatePumpTime3);
		etPumpTime[3] = (EditText) v.findViewById(R.id.etOperatePumpTime4);
		btnPumpStop[0] = (Button) v.findViewById(R.id.btnStopPump1);
		btnPumpStop[1] = (Button) v.findViewById(R.id.btnStopPump2);
		btnPumpStop[2] = (Button) v.findViewById(R.id.btnStopPump3);
		btnPumpStop[3] = (Button) v.findViewById(R.id.btnStopPump4);
		btnPumpStart[0] = (Button) v.findViewById(R.id.btnStartPump1);
		btnPumpStart[1] = (Button) v.findViewById(R.id.btnStartPump2);
		btnPumpStart[2] = (Button) v.findViewById(R.id.btnStartPump3);
		btnPumpStart[3] = (Button) v.findViewById(R.id.btnStartPump4);
		btnPumpParams[0] = (Button) v.findViewById(R.id.btnPumpParam1);
		btnPumpParams[1] = (Button) v.findViewById(R.id.btnPumpParam2);
		btnPumpParams[2] = (Button) v.findViewById(R.id.btnPumpParam3);
		btnPumpParams[3] = (Button) v.findViewById(R.id.btnPumpParam4);
		pumpView[0] = v.findViewById(R.id.linearLayoutPump1);
		pumpView[1] = v.findViewById(R.id.linearLayoutPump2);
		pumpView[2] = v.findViewById(R.id.linearLayoutPump3);
		pumpView[3] = v.findViewById(R.id.linearLayoutPump4);
		swRobotArmScan = (Switch) v.findViewById(R.id.swOperateSacnRobotArm);
		tvRobotArmPos = (TextView) v.findViewById(R.id.tvOperateRobotArmPos);

		etPoseNum = (EditText) v.findViewById(R.id.etOperatePoseNum);
		btnZeroPose = (Button) v.findViewById(R.id.btnOperateZero);
		btnSetZeroPose = (Button) v.findViewById(R.id.btnOperateSetZero);
		btnSavePose = (Button) v.findViewById(R.id.btnOperateSavePose);
        robotArmView = v.findViewById(R.id.LinearLayoutRobotArmState);

		test1 = (Button) v.findViewById(R.id.btnOperateTest1);
		test2 = (Button) v.findViewById(R.id.btnOperateTest2);
		test1.setOnClickListener(this);
		test2.setOnClickListener(this);

        btnInjectionPumpInit = (Button) v.findViewById(R.id.btnOperateInjectionPumpInit);
        btnInjectionPumpMove = (Button) v.findViewById(R.id.btnOperateInjectionPumpMove);
        swInjectionPumpValve = (Switch) v.findViewById(R.id.swOperateInjectionPumpValve);
        etInjectionPumpMove = (EditText) v.findViewById(R.id.etOperateInjectionPumpMove);
        btnInjectionPumpInit.setOnClickListener(this);
        btnInjectionPumpMove.setOnClickListener(this);
        injectionPumpView = v.findViewById(R.id.linearLayoutInjectionPump);

        btnZeroPose.setOnClickListener(this);
		btnSetZeroPose.setOnClickListener(this);
		btnSavePose.setOnClickListener(this);
		swRobotArmScan.setOnClickListener(this);
		btnInit.setOnClickListener(this);
		btnClear.setOnClickListener(this);
		tbChangMaintenanceModel.setOnClickListener(this);
		for (int i = 0; i < btnCalTemp.length; i++) {
			btnCalTemp[i].setOnClickListener(this);			
		}
		btnGetDectectInfo.setOnClickListener(this);
		btnGetDevicesInfo.setOnClickListener(this);
		for (int i = 0; i < tbVDSwtich.length; i++) {
			tbVDSwtich[i].setOnClickListener(this);			
		}
		for (int i = 0; i < tbValveSwich.length; i++) {
			tbValveSwich[i].setOnClickListener(this);			
		}
		for (int i = 0; i < btnPumpStart.length; i++) {
			btnPumpStart[i].setOnClickListener(this);			
		}
		for (int i = 0; i < btnPumpStop.length; i++) {
			btnPumpStop[i].setOnClickListener(this);			
		}
		for (int i = 0; i < btnPumpParams.length; i++) {
			btnPumpParams[i].setOnClickListener(this);			
		}
	}

	@Override
	public void showParams(String data) {
		// TODO 自动生成的方法存根
		tvDectectInfo.setText(data);
	}

	@Override
	public void showDevices(DevicesData data) {
		// TODO 自动生成的方法存根
		/*if (key) {
			
		}*/
		for (int i = 0; i < tbValveSwich.length; i++) {
			tbValveSwich[i].setChecked(data.getValve(i));			
		}
		for (int i = 0; i < tbVDSwtich.length; i++) {
			tbVDSwtich[i].setChecked(data.getVirtual(i));
		}
	}

	@Override
	public void showMaintenanceDetail(EquipmentData data) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "载入"+String.valueOf(data.isMaintance()));
		tbChangMaintenanceModel.setChecked(data.isMaintance());
		for (int i = 0; i < tbValveSwich.length; i++) {
			if (data.getValveState(i)) {	
				tbValveSwich[i].setVisibility(View.VISIBLE);
			}
			else {
				tbValveSwich[i].setVisibility(View.INVISIBLE);
			}			
		}
		
		for (int i = 0; i < tbVDSwtich.length; i++) {
			if (data.getVDState(i)) {				
				tbVDSwtich[i].setVisibility(View.VISIBLE);
				tbVDSwtich[i].setTextOn(data.getVDOnString(i));
				tbVDSwtich[i].setTextOff(data.getVDOffString(i));
				tbVDSwtich[i].setChecked(false);
			}
			else {
				tbVDSwtich[i].setVisibility(View.INVISIBLE);
			}	
		}
		
		for (int i = 0; i < pumpView.length; i++) {
			if (data.getPumpState(i)) {				
				pumpView[i].setVisibility(View.VISIBLE);
			}
			else {
				pumpView[i].setVisibility(View.GONE);
			}
		}

		if(data.isHasInjectionPump()){
            injectionPumpView.setVisibility(View.VISIBLE);
        }else{
            injectionPumpView.setVisibility(View.GONE);
        }

        if(data.isHasRobotArm()){
            robotArmView.setVisibility(View.VISIBLE);
        }else{
            robotArmView.setVisibility(View.GONE);
        }
	}

	@Override
	public void showMaintenanceIcon(boolean key) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "showMaintenanceIcon");
		String string = getString(R.string.software_versions).substring(0, 3);
		if (string.equals("dev")) {
			detailMaintenance.setVisibility(View.VISIBLE);
		}else {			
			if (key) {
				detailMaintenance.setVisibility(View.VISIBLE);
			}else {
				detailMaintenance.setVisibility(View.INVISIBLE);
			}
		}
		
		Intent intent = new Intent();
		intent.setAction("maintanceSign");
		intent.putExtra("key", key);
		getActivity().sendBroadcast(intent);
	}

	
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		Log.d(tag, "onDestroy");
		//presenter.MaintenanceModelSwitch(false);
		
		/*Intent intent = new Intent();
		intent.setAction("maintanceSign");
		intent.putExtra("key", false);
		getActivity().sendBroadcast(intent);*/
		robotArm.stopScan();
		super.onDestroy();
	}

	@Override
	public void showRealTimePos(String string) {
		robotArmStateString = string;
		handler.sendEmptyMessage(msgShowRobotState);
	}

	@Override
	public void showPose(int num, String string) {
		if((num <= 20)&&(num>0)){
			showPoseNum = num-1;
			PoseString = string;
			handler.sendEmptyMessage(msgShowPose);
		}
	}

	class ReadState extends AsyncTask<String, Void, Object>{
		private ProgressDialog pd;  

		@Override
		protected Object doInBackground(String... params) {
			// TODO 自动生成的方法存根
			Object data = null;
			Log.d(tag, params[0].toString());
			if (params[0].equals("GetDevices")) {
				data = presenter.getDevices();
			}
			else if (params[0].equals("GetDectect")) {
				data = presenter.getParams();
			}
			else {
				
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return data;
		}
		
		protected void onPostExecute(Object result) {
			if (result != null) {
				if (result.toString().equals("DevicesData")) {					
					showDevices((DevicesData) result);
				}
				else {
					tvDectectInfo.setText((String)result);
				}
			}
			else {
				Toast.makeText(getActivity(), "仪器故障", Toast.LENGTH_SHORT).show();
			}
			pd.dismiss();
		};
		
		@Override
		protected void onPreExecute() {
			// TODO 自动生成的方法存根
			super.onPreExecute();
			pd= new ProgressDialog(getActivity());
			pd.setMax(100);
			pd.setTitle("提示");
			pd.setMessage("系统查询中，其稍候..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCanceledOnTouchOutside(false);
			pd.show();
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
            case R.id.swOperateSacnRobotArm:
                    if(swRobotArmScan.isChecked()){
                        robotArm.startScan();
                    }else {
                        robotArm.stopScan();
                    }
                    break;
            case R.id.btnOperateInit:
                if (presenter.init()) {
                    Toast.makeText(getActivity(), "开始初始化", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnOperateClear:
                if (presenter.clear()) {
                    Toast.makeText(getActivity(), "开始清洗", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "系统正忙，请稍候再试", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tbOperateMaintenModel:
                if ((ScriptRun.getInstance().isRun())&&(tbChangMaintenanceModel.isChecked())) {
                    tbChangMaintenanceModel.setChecked(false);
                    Toast.makeText(getActivity(), "系统正在运行，请勿进行维护操作!", Toast.LENGTH_SHORT).show();
                }
                else {
                    presenter.MaintenanceModelSwitch(tbChangMaintenanceModel.isChecked());
                    if(!tbChangMaintenanceModel.isChecked()){
                        robotArm.stopScan();
                    }
                }
                break;
            case R.id.btnTempParam1:
                presenter.calTemp(7, Float.valueOf(etTempParams[0].getText().toString()));
                break;
            case R.id.btnTempParam2:
                presenter.calTemp(8, Float.valueOf(etTempParams[1].getText().toString()));
                break;
            case R.id.btnGetDetector:
                ReadState read = new ReadState();
                read.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "GetDectect");
                break;
            case R.id.btnGetDevices:
                ReadState devices = new ReadState();
                devices.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "GetDevices");
                break;
            case R.id.tbOperateVD1:
                presenter.setVirtual(0, tbVDSwtich[0].isChecked());
                break;
            case R.id.tbOperateVD2:
                presenter.setVirtual(1, tbVDSwtich[1].isChecked());
                break;
            case R.id.tbOperateVD3:
                presenter.setVirtual(2, tbVDSwtich[2].isChecked());
                break;
            case R.id.tbOperateVD4:
                presenter.setVirtual(3, tbVDSwtich[3].isChecked());
                break;
            case R.id.tbOperateVD5:
                presenter.setVirtual(4, tbVDSwtich[4].isChecked());
                break;
            case R.id.tbOperateV1:
                presenter.setValve(1, tbValveSwich[0].isChecked());
                break;
            case R.id.tbOperateV2:
                presenter.setValve(2, tbValveSwich[1].isChecked());
                break;
            case R.id.tbOperateV3:
                presenter.setValve(3, tbValveSwich[2].isChecked());
                break;
            case R.id.tbOperateV4:
                presenter.setValve(4, tbValveSwich[3].isChecked());
                break;
            case R.id.tbOperateV5:
                presenter.setValve(5, tbValveSwich[4].isChecked());
                break;
            case R.id.tbOperateV6:
                presenter.setValve(6, tbValveSwich[5].isChecked());
                break;
            case R.id.tbOperateV7:
                presenter.setValve(7, tbValveSwich[6].isChecked());
                break;
            case R.id.tbOperateV8:
                presenter.setValve(8, tbValveSwich[7].isChecked());
                break;
            case R.id.tbOperateV9:
                presenter.setValve(9, tbValveSwich[8].isChecked());
                break;
            case R.id.tbOperateV10:
                presenter.setValve(10, tbValveSwich[9].isChecked());
                break;
            case R.id.tbOperateV11:
                presenter.setValve(11, tbValveSwich[10].isChecked());
                break;
            case R.id.tbOperateV12:
                presenter.setValve(12, tbValveSwich[11].isChecked());
                break;
            case R.id.tbOperateV13:
                presenter.setValve(13, tbValveSwich[12].isChecked());
                break;
            case R.id.tbOperateV14:
                presenter.setValve(14, tbValveSwich[13].isChecked());
                break;
            case R.id.tbOperateV15:
                presenter.setValve(15, tbValveSwich[14].isChecked());
                break;
            case R.id.tbOperateV16:
                presenter.setValve(16, tbValveSwich[15].isChecked());
                break;
            case R.id.tbOperateV17:
                presenter.setValve(17, tbValveSwich[16].isChecked());
                break;
            case R.id.tbOperateV18:
                presenter.setValve(18, tbValveSwich[17].isChecked());
                break;
            case R.id.tbOperateV19:
                presenter.setValve(19, tbValveSwich[18].isChecked());
                break;
            case R.id.tbOperateV20:
                presenter.setValve(20, tbValveSwich[19].isChecked());
                break;
            case R.id.tbOperateV21:
                presenter.setValve(21, tbValveSwich[20].isChecked());
                break;
            case R.id.tbOperateV22:
                presenter.setValve(22, tbValveSwich[21].isChecked());
                break;
            case R.id.tbOperateV23:
                presenter.setValve(23, tbValveSwich[22].isChecked());
                break;
            case R.id.tbOperateV24:
                presenter.setValve(24, tbValveSwich[23].isChecked());
                break;
            case R.id.tbOperateV25:
                presenter.setValve(25, tbValveSwich[24].isChecked());
                break;
            case R.id.tbOperateV26:
                presenter.setValve(26, tbValveSwich[25].isChecked());
                break;
            case R.id.tbOperateV27:
                presenter.setValve(27, tbValveSwich[26].isChecked());
                break;
            case R.id.tbOperateV28:
                presenter.setValve(28, tbValveSwich[27].isChecked());
                break;
            case R.id.btnPumpParam1:
                presenter.setPumpParams(1,Float.valueOf(etPumpParams[0].getText().toString()));
                break;
            case R.id.btnPumpParam2:
                presenter.setPumpParams(2,Float.valueOf(etPumpParams[1].getText().toString()));
                break;
            case R.id.btnPumpParam3:
                presenter.setPumpParams(3,Float.valueOf(etPumpParams[2].getText().toString()));
                break;
            case R.id.btnPumpParam4:
                presenter.setPumpParams(4,Float.valueOf(etPumpParams[3].getText().toString()));
                break;
            case R.id.btnStopPump1:
                presenter.stopPump(1);
                break;
            case R.id.btnStopPump2:
                presenter.stopPump(2);
                break;
            case R.id.btnStopPump3:
                presenter.stopPump(3);
                break;
            case R.id.btnStopPump4:
                presenter.stopPump(4);
                break;
            case R.id.btnStartPump1:
                presenter.startPump(1,Integer.valueOf(etPumpRound[0].getText().toString()) , Integer.valueOf(etPumpTime[0].getText().toString()));
                break;
            case R.id.btnStartPump2:
                presenter.startPump(2,Integer.valueOf(etPumpRound[1].getText().toString()) , Integer.valueOf(etPumpTime[1].getText().toString()));
                break;
            case R.id.btnStartPump3:
                presenter.startPump(3,Integer.valueOf(etPumpRound[2].getText().toString()) , Integer.valueOf(etPumpTime[2].getText().toString()));
                break;
            case R.id.btnStartPump4:
                presenter.startPump(4,Integer.valueOf(etPumpRound[3].getText().toString()) , Integer.valueOf(etPumpTime[3].getText().toString()));
                break;
			case R.id.btnOperateTest1:
				robotArm.jump2Pose(150,-139,14);
				break;
			case R.id.btnOperateTest2:
				robotArm.jump2Pose(45,-246,-35);
				break;
			case R.id.btnOperateZero:
				robotArm.jump2Home();
				break;
			case R.id.btnOperateSetZero:
				robotArm.setHomeParams();
				break;
			case R.id.btnOperateSavePose:
				robotArm.savePose(etPoseNum.getText().toString());
				break;
            case R.id.btnOperateInjectionPumpInit:
                injectionPump.initPump();
                break;
            case R.id.btnOperateInjectionPumpMove:
                injectionPump.go2Pose(swInjectionPumpValve.isChecked(),Integer.valueOf(etInjectionPumpMove.getText().toString()));
                break;
		default:
			break;
		}
	}

	private class ClickPose implements OnClickListener{
		private TextView [] tvBuff;
		public void showPoses(String [] strings){
			if(strings.length==20){
				for(int i=0;i<20;i++){
					tvBuff[i].setText(strings[i]);
				}
			}
		}

		public ClickPose (TextView [] tv,View v){
			if(tv.length == 20){
				Log.d(tag,"初始化Poses");
				this.tvBuff = tv;
				tv[0] = (TextView) v.findViewById(R.id.tvOperatePose11);
				tv[1] = (TextView) v.findViewById(R.id.tvOperatePose12);
				tv[2] = (TextView) v.findViewById(R.id.tvOperatePose13);
				tv[3] = (TextView) v.findViewById(R.id.tvOperatePose14);
				tv[4] = (TextView) v.findViewById(R.id.tvOperatePose15);
				tv[5] = (TextView) v.findViewById(R.id.tvOperatePose21);
				tv[6] = (TextView) v.findViewById(R.id.tvOperatePose22);
				tv[7] = (TextView) v.findViewById(R.id.tvOperatePose23);
				tv[8] = (TextView) v.findViewById(R.id.tvOperatePose24);
				tv[9] = (TextView) v.findViewById(R.id.tvOperatePose25);
				tv[10] = (TextView) v.findViewById(R.id.tvOperatePose31);
				tv[11] = (TextView) v.findViewById(R.id.tvOperatePose32);
				tv[12] = (TextView) v.findViewById(R.id.tvOperatePose33);
				tv[13] = (TextView) v.findViewById(R.id.tvOperatePose34);
				tv[14] = (TextView) v.findViewById(R.id.tvOperatePose35);
				tv[15] = (TextView) v.findViewById(R.id.tvOperatePose41);
				tv[16] = (TextView) v.findViewById(R.id.tvOperatePose42);
				tv[17] = (TextView) v.findViewById(R.id.tvOperatePose43);
				tv[18] = (TextView) v.findViewById(R.id.tvOperatePose44);
				tv[19] = (TextView) v.findViewById(R.id.tvOperatePose45);
				for(int i=0;i<20;i++){
					tv[i].setOnClickListener(this);
				}
			}
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.tvOperatePose11:
					robotArm.go2Pose(1);
					break;
				case R.id.tvOperatePose12:
					robotArm.go2Pose(2);
					break;
				case R.id.tvOperatePose13:
					robotArm.go2Pose(3);
					break;
				case R.id.tvOperatePose14:
					robotArm.go2Pose(4);
					break;
				case R.id.tvOperatePose15:
					robotArm.go2Pose(5);
					break;
				case R.id.tvOperatePose21:
					robotArm.go2Pose(6);
					break;
				case R.id.tvOperatePose22:
					robotArm.go2Pose(7);
					break;
				case R.id.tvOperatePose23:
					robotArm.go2Pose(8);
					break;
				case R.id.tvOperatePose24:
					robotArm.go2Pose(9);
					break;
				case R.id.tvOperatePose25:
					robotArm.go2Pose(10);
					break;
				case R.id.tvOperatePose31:
					robotArm.go2Pose(11);
					break;
				case R.id.tvOperatePose32:
					robotArm.go2Pose(12);
					break;
				case R.id.tvOperatePose33:
					robotArm.go2Pose(13);
					break;
				case R.id.tvOperatePose34:
					robotArm.go2Pose(14);
					break;
				case R.id.tvOperatePose35:
					robotArm.go2Pose(15);
					break;
				case R.id.tvOperatePose41:
					robotArm.go2Pose(16);
					break;
				case R.id.tvOperatePose42:
					robotArm.go2Pose(17);
					break;
				case R.id.tvOperatePose43:
					robotArm.go2Pose(18);
					break;
				case R.id.tvOperatePose44:
					robotArm.go2Pose(19);
					break;
				case R.id.tvOperatePose45:
					robotArm.go2Pose(20);
					break;
			}
		}
	}
	
	

}
