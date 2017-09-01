package cn.com.grean.monitoring;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cn.com.grean.DeviceControlPanel;
import cn.com.grean.Dwin;
import cn.com.grean.myApplication;
import cn.com.grean.tools;
import cn.com.grean.protocol.ProtocolProcessorImp;
import cn.com.grean.script.ScriptRun;
import cn.com.grean.script.plan.AutoCalPlan;
import cn.com.grean.script.plan.AutoTestPlan;
import cn.com.grean.script.plan.ScheduleListener;
import cn.com.grean.script.plan.ScheduleOfScriptRun;
import cn.com.grean.script.plan.ScriptChain;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener,ScheduleListener,DeviceControlPanel{
	private final static String tag = "MainActivity";
	private final static int AUTO_TEST = 1;
	private final static int AUTO_CAL = 2;
	private final static int RESTART_TEST = 3;
	private final static int RESTART_CAL=4;
	private final static int TurnOnBackLight = 5;
	private final static int TurnOffBackLight = 6;
	private final static int SYSTEM_INIT = 7;
	
	private View measureLayout;
	private View operateLayout;
	private View settingLayout;
	private View logLayout;
	
	/*private final static String measureTag = "MeasureFragment";
	private final static String lgoTag = "LogFragment";
	private final static String operateTag = "OperationFragment";
	private final static String settingTag = "SettingFragment";*/
	
	private MeasureFragment measureFragment;
	private LogFragment logFragment;
	private OperationFragment operationFragment;
	private SettingFragment settingFragment;
	
	
	private FragmentManager fragmentManager; 
	private Fragment lastFragment;
	
	private ImageView ivHome;
	private TextView tvHome;
	private ImageView ivOperate;
	private TextView tvOperate;
	private ImageView ivSetting;
	private TextView tvSetting;
	private ImageView ivData;
	private TextView tvData;	
	
	private Timer autoTest;
	private Timer autoCalibration;
	
	private int backLightMax = 300;
	private int backLightCount =0;
	private boolean backLightRun = true;
	private boolean backLightIsOn = false;//当前背光状态

	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			ScriptRun run = ScriptRun.getInstance();
			switch (msg.what) {
			case SYSTEM_INIT:
				if (!ScriptRun.getInstance().isRun()){
					ScriptChain chain = new ScriptChain("初始化");				
					run.addChainOfScript(chain);
					run.startSchedule(tools.nowtime2timestamp(), "None");
				}
				break;
			case AUTO_TEST:
				//autoTestTimerTask.cancel();
				if (ScriptRun.getInstance().isRun()||myApplication.getInstance().isMaintenaning()) {
					autoTest = new Timer();
					autoTest.schedule(new AutoTestTimeTask(), 60000l);
				}
				else {			
					Intent intent = new Intent();
					intent.setAction("nextTest");
					intent.putExtra("dateString", "自动测量中...");	
					sendBroadcast(intent);
					ScriptChain chain = new ScriptChain("测量");				
					run.addChainOfScript(chain);
					run.startSchedule(tools.nowtime2timestamp(), "AUTOTest");
				}
				break;
			case AUTO_CAL:
				if (ScriptRun.getInstance().isRun()||myApplication.getInstance().isMaintenaning()) {
					autoCalibration = new Timer();
					autoCalibration.schedule(new AutoCalTimerTask(), 60000l);
				}
				else {		
					Intent intent = new Intent();
					intent.setAction("nextCal");
					intent.putExtra("dateString", "自动校准中...");	
					sendBroadcast(intent);
					ScriptChain step1 = new ScriptChain("低点校准");	
					ScriptChain step2 = new ScriptChain("高点校准");
					run.addChainOfScript(step1);
					run.addChainOfScript(step2);
					run.startSchedule(tools.nowtime2timestamp(), "AUTOCalibration");
				}
				break;
			case RESTART_TEST:
				run.startSchedule(tools.nowtime2timestamp(), "AUTOTest");
				break;
			case RESTART_CAL:
				run.startSchedule(tools.nowtime2timestamp(), "AUTOCalibration");
				break;
			case TurnOffBackLight:
				Log.d(tag, "关背光");
				//Dwin.setBrightness(MainActivity.this, 80);
				//Dwin.getInstance().setBrightness(0);
				Dwin.getInstance().setBrightness(100);
				break;
			case TurnOnBackLight:
				Log.d(tag, "开背光");
				Dwin.getInstance().setBrightness(240);
				//Dwin.setBrightness(MainActivity.this, 240);
				break;
			default:
				break;
			}
			
		};
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dwin.getInstance().hideNavigation();
        initView();
        ScriptRun.getInstance().setScheduleListener(this);
        fragmentManager = getFragmentManager();  
        
       /* measureFragment = new MeasureFragment();
        logFragment = new LogFragment();
        operationFragment = new OperationFragment();
        settingFragment = new SettingFragment();*/
        
        setTabSelection(0);        
        IntentFilter filter_dynamic = new IntentFilter();  
        filter_dynamic.addAction("UpdatAutoInfo");  //更新下次测量时间
        filter_dynamic.addAction("UpdatAutoCalInfo");
        registerReceiver(dynamicReceiver, filter_dynamic);
        myApplication.getInstance().getCompute();
       // myApplication.getInstance().
        new Thread(new BackLightCtrl()).start();
        
        ScheduleOfScriptRun run;
        if (myApplication.getInstance().getConfigBoolean("AutoTestModel")) {			
        	run = new AutoTestPlan();
        	run.timing(this, run.getNexDate(myApplication.getInstance().getConfigLong("AutoTestDate")));
		}
        if (myApplication.getInstance().getConfigBoolean("AutoCalModel")) {
        	run = new AutoCalPlan();
        	run.timing(this, run.getNexDate(myApplication.getInstance().getConfigLong("AutoCalDate")));
		}
        ProtocolProcessorImp.getInstance().setPanel(this);        
        //Dwin.setBrightness(MainActivity.this, 1);
        Dwin.getInstance().setBrightness(200);
        handler.sendEmptyMessageDelayed(SYSTEM_INIT, 10000l);
        
    }
    
    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			if (intent.getAction().equals("UpdatAutoInfo")) {
				ScriptRun.getInstance().cancelCurrentEnd("AUTOTest");
				if (intent.getBooleanExtra("enable", false)) {
					cancelAutoTest();				
					autoTest = new Timer();					
					Date when = new Date(intent.getLongExtra("date", 0l));
					autoTest.schedule(new AutoTestTimeTask(), when);
				}
				else {
					cancelAutoTest();
				}
			}
			else if (intent.getAction().equals("UpdatAutoCalInfo")) {
				ScriptRun.getInstance().cancelCurrentEnd("AUTOCalibration");
				if (intent.getBooleanExtra("enable", false)) {
					cancelAutoCal();
					autoCalibration = new Timer();
					Date when = new Date(intent.getLongExtra("date", 0l));
					autoCalibration.schedule(new AutoCalTimerTask(), when);
				}
				else {
					cancelAutoCal();
				}
			}
		}
    	
    };
   
    private void initView(){
    	tvData = (TextView) findViewById(R.id.data_text);
    	tvHome = (TextView) findViewById(R.id.home_text);
    	tvOperate = (TextView) findViewById(R.id.operate_text);
    	tvSetting = (TextView) findViewById(R.id.setting_text);
    	ivData = (ImageView) findViewById(R.id.data_image);
    	ivHome = (ImageView) findViewById(R.id.home_image);
    	ivOperate = (ImageView) findViewById(R.id.operate_image);
    	ivSetting = (ImageView) findViewById(R.id.setting_image);
    	
    	measureLayout = findViewById(R.id.measure_layout);
    	operateLayout = findViewById(R.id.operation_layout);
    	settingLayout = findViewById(R.id.setting_layout);
    	logLayout = findViewById(R.id.log_layout);
    	measureLayout.setOnClickListener(this);
    	operateLayout.setOnClickListener(this);
    	settingLayout.setOnClickListener(this);
    	logLayout.setOnClickListener(this);
    }
    
    

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.measure_layout:
			setTabSelection(0);
			break;
		case R.id.operation_layout:
			setTabSelection(1);
			break;
		case R.id.setting_layout:
			setTabSelection(2);
			break;
		case R.id.log_layout:
			setTabSelection(3);
			break;
		default:
			break;
		}
		
	}
	
    /** 
     * 根据传入的index参数来设置选中的tab页。 
     *  
     * @param index 
     *            每个tab页对应的下标。0表示主界面，1表示操作，2表示设置，3表示历史。 
     */  
    private void setTabSelection(int index) {  
        // 每次选中之前先清楚掉上次的选中状态  
        clearSelection();  
        // 开启一个Fragment事务  
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        //hideFragments(transaction,lastFragment); 
        hideFragments(transaction);
       /* if (lastFragment!=null) {
			Log.d(tag,"lastFragment = "+lastFragment.getClass().getName());
		}*/
        switch (index) {  
        case 0:  
            // 当点击了消息tab时，改变控件的图片和文字颜色  
            ivHome.setImageResource(R.drawable.home_selected);
            tvHome.setTextColor(Color.WHITE);
        	//measureFragment.setRetainInstance(true);
        	
        	
            if (measureFragment == null) {  
            	//Log.d(tag, "measureFragment = null");
                // 如果MessageFragment为空，则创建一个并添加到界面上  
            	measureFragment = new MeasureFragment();  
            	/*if (lastFragment!=null) {
					transaction.hide(lastFragment);
				}*/
                transaction.add(R.id.content, measureFragment,"MeasureFragment").commit();  
            } else {  
            	//Log.d(tag, "measureFragment != null");
                // 如果MessageFragment不为空，则直接将它显示出来  
                //transaction.attach(measureFragment);
            	/*if (lastFragment!=null) {
            		if (!lastFragment.getTag().equals("MeasureFragment")) {						
            			transaction.hide(lastFragment).show(measureFragment).commit();
					}
        		}
            	else {					
            		transaction.show(measureFragment).commit(); 
				}   */
            	if (measureFragment.isAdded()) {
					
            		transaction.show(measureFragment).commit(); 
				}
            	else {
            		transaction.add(R.id.content, measureFragment,"MeasureFragment").commit();  
				}
            	
            }  
            
            //transaction.replace(R.id.content, measureFragment);
            lastFragment = fragmentManager.findFragmentByTag("MeasureFragment");
            break;  
        case 1:  
            // 当点击了联系人tab时，改变控件的图片和文字颜色  
           ivOperate.setImageResource(R.drawable.operate_selected);
           tvOperate.setTextColor(Color.WHITE);
            if (operationFragment == null) {  
            	//Log.d(tag, "operationFragment = null");
                // 如果ContactsFragment为空，则创建一个并添加到界面上  
            	/*if (lastFragment!=null) {
					transaction.hide(lastFragment);
				}*/
                operationFragment = new OperationFragment();  
                transaction.add(R.id.content, operationFragment,"OperateFragment").commit();  
            } else {  
            	//Log.d(tag, "operationFragment != null");
                // 如果ContactsFragment不为空，则直接将它显示出来  
            	//Log.d(tag, "operationFragment="+String.valueOf(operationFragment.isVisible()));
            	/*if (lastFragment!=null) {
					if (!lastFragment.getTag().equals("OperateFragment")) {
						transaction.hide(lastFragment).show(operationFragment).commit();
					}
				}
            	else {
					
            		transaction.show(operationFragment).commit(); 
				}*/
            	if (operationFragment.isAdded()) {
					
            		transaction.show(operationFragment).commit(); 
				}
            	else {
            		transaction.add(R.id.content, operationFragment,"OperateFragment").commit();  
				}
            	/*if (operationFragment.isVisible()) {
					
				}
            	else {
            		transaction.add(R.id.content, operationFragment);
				}*/
            	/*transaction.remove(operationFragment).commit();
                transaction.add(R.id.content, operationFragment);  */
            	//transaction.attach(operationFragment);
            }
            //transaction.replace(R.id.content, operationFragment);
            lastFragment = fragmentManager.findFragmentByTag("OperateFragment");
            break;  
        case 2:  
            // 当点击了动态tab时，改变控件的图片和文字颜色  
	        ivSetting.setImageResource(R.drawable.setting_selected);
	        tvSetting.setTextColor(Color.WHITE);
            if (settingFragment == null) {  
            	//Log.d(tag, "settingFragment = null");
                // 如果NewsFragment为空，则创建一个并添加到界面上  
            	/*if (lastFragment!=null) {
					transaction.hide(lastFragment);
				}*/
            	settingFragment = new SettingFragment();  
                transaction.add(R.id.content, settingFragment,"SettingFragment").commit();  
            } else {  
            	//Log.d(tag, "settingFragment != null");

                // 如果NewsFragment不为空，则直接将它显示出来  
                //transaction.attach(settingFragment);
            	
            	/*if (!lastFragment.getTag().equals("SettingFragment")) {
					transaction.hide(lastFragment).show(settingFragment).commit();
				}
            	else {
					transaction.show(settingFragment).commit();  
				}*/
            	if (settingFragment.isAdded()) {
					
            		transaction.show(settingFragment).commit();  
				}
            	else {
            		transaction.add(R.id.content, settingFragment,"SettingFragment").commit();  
				}
            	/*transaction.remove(settingFragment).commit();
            	transaction.add(R.id.content, settingFragment); */ 
            } 
            //transaction.replace(R.id.content, settingFragment);
            lastFragment = fragmentManager.findFragmentByTag("SettingFragment");
            break;  
        case 3:  
        default:  
            // 当点击了设置tab时，改变控件的图片和文字颜色  
            ivData.setImageResource(R.drawable.data_selected);
            tvData.setTextColor(Color.WHITE);
            if (logFragment == null) {  
            	//Log.d(tag, "logFragment = null");
            	/*if (lastFragment!=null) {
					transaction.hide(lastFragment);
				}*/
                // 如果SettingFragment为空，则创建一个并添加到界面上  
                logFragment = new LogFragment();  
                transaction.add(R.id.content, logFragment,"LogFragment").commit();  
            } else {  
            	/*Log.d(tag, "logFragment != null");
            	Log.d(tag, "logFragment="+String.valueOf(logFragment.isVisible()));*/
                // 如果SettingFragment不为空，则直接将它显示出来  
            	//transaction.attach(logFragment);
            	/*if (lastFragment!=null) {
					if (!lastFragment.getTag().equals("LogFragment")) {
						transaction.hide(lastFragment).show(logFragment).commit();
					}
				}
            	else {
            		transaction.show(logFragment).commit();  
					
				}*/
            	if (logFragment.isAdded()) {
					
            		transaction.show(logFragment).commit();  
				}
            	else {
            		transaction.add(R.id.content, logFragment,"LogFragment").commit();  
				}
            	/*transaction.remove(logFragment).commit();
            	transaction.add(R.id.content, logFragment);  */
            }  
            
            //transaction.replace(R.id.content, logFragment);
            lastFragment = fragmentManager.findFragmentByTag("LogFragment");
            break;  
        }  

        //lastFragment = index;
        //transaction.commit();  
        //transaction.commitAllowingStateLoss();
        //fragmentManager.executePendingTransactions();
    }  
  
    /** 
     * 清除掉所有的选中状态。 
     */  
    private void clearSelection() {  
    	ivHome.setImageResource(R.drawable.home_unselected);
    	ivData.setImageResource(R.drawable.data_unselected);
    	ivOperate.setImageResource(R.drawable.operate_unselected);
    	ivSetting.setImageResource(R.drawable.setting_unselected);
    	tvData.setTextColor(Color.parseColor("#dbdbdb"));
    	tvHome.setTextColor(Color.parseColor("#dbdbdb"));
    	tvOperate.setTextColor(Color.parseColor("#dbdbdb"));
    	tvSetting.setTextColor(Color.parseColor("#dbdbdb"));
    } 
    
    /** 
     * 将所有的Fragment都置为隐藏状态。 
     *  
     * @param transaction 
     *            用于对Fragment执行操作的事务 
     */  
    private void hideFragments(FragmentTransaction transaction) {  
        if (measureFragment != null) {  
            transaction.hide(measureFragment);  
        }  
        if (operationFragment != null) {  
            transaction.hide(operationFragment);  
        }  
        if (settingFragment != null) {  
            transaction.hide(settingFragment);  
        }  
        if (logFragment != null) {  
            transaction.hide(logFragment);  
        }  
    }
    
    private void hideFragments(FragmentTransaction transaction,int num){
    	switch (num) {
		case 0:
			if (measureFragment != null) {  
	            transaction.hide(measureFragment);  
	        }  
			break;
		case 1:
			if (operationFragment != null) {  
	            transaction.hide(operationFragment);  
	        }  
			break;
		case 2:
			if (settingFragment != null) {  
	            transaction.hide(settingFragment);  
	        }  
			break;
		case 3:
			if (logFragment != null) {  
	            transaction.hide(logFragment);  
	        }  
			break;

		default:
			break;
		}
    }
	
	class AutoTestTimeTask extends TimerTask{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			handler.sendEmptyMessage(AUTO_TEST);
			autoTest.cancel();
		}
		
	}
	
	class AutoCalTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			handler.sendEmptyMessage(AUTO_CAL);
			
		}
		
	}
	
	class AutoTestRepeatTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			handler.sendEmptyMessage(RESTART_TEST);
			if (autoTest != null) {
				autoTest.cancel();
			}
		}
		
	}
	
	
	
	class AutoCalRepeatTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			handler.sendEmptyMessage(RESTART_CAL);
			autoCalibration.cancel();
		}
		
	}

	@Override
	public void notify(int kind, boolean key,long date) {
		// TODO 自动生成的方法存根
		Log.d(tag, "notify,"+String.valueOf(kind)+","+String.valueOf(key));
		Intent intent = new Intent();
		switch (kind) {
		case ScheduleListener.AUTOCalibration:
			intent.setAction("nextCal");
			if (key) {
				
				autoCalibration = new Timer();
				Date when = new Date(date);
				autoCalibration.schedule(new AutoCalTimerTask(), when);
				intent.putExtra("dateString", "下次校准时间:"+tools.timestamp2string(date));			
			}
			else {
				intent.putExtra("dateString",  "下次校准时间:");
				cancelAutoCal();
			}
			
			sendBroadcast(intent);
			break;
		case ScheduleListener.AUTOTest:
			intent.setAction("nextTest");
			if (key) {				
				autoTest = new Timer();
				Date when = new Date(date);
				autoTest.schedule(new AutoTestTimeTask(), when);
				intent.putExtra("dateString", "下次测量时间:"+tools.timestamp2string(date));		
			}
			else {
				cancelAutoTest();
				intent.putExtra("dateString",  "下次测量时间:");
			}
			sendBroadcast(intent);
			break;
		case ScheduleListener.CancelAll:
			cancelAllTimer();
			break;
		default:
			break;
		}
	}

	private void cancelAllTimer(){
		cancelAutoCal();
		cancelAutoTest();
	}
	
	private void cancelAutoTest(){
		if (autoTest != null) {
			autoTest.cancel();
			autoTest = null;
		}
	}
	
	private void cancelAutoCal(){
		if (autoCalibration != null) {
			autoCalibration.cancel();
			autoCalibration = null;
		}
	}

	@Override
	public void isBusy(int kind) {
		// TODO 自动生成的方法存根
		Log.d(tag, "isBusy"+String.valueOf(kind));
		switch (kind) {
		case ScheduleListener.AUTOCalibration:			
			autoCalibration = new Timer();
			autoCalibration.schedule(new AutoCalRepeatTimerTask(), 60000l);
			break;
		case ScheduleListener.AUTOTest:			
			autoTest = new Timer();
			autoCalibration.schedule(new AutoTestRepeatTimerTask(), 60000l);
			break;
		case ScheduleListener.CancelAll:
			cancelAllTimer();
			break;

		default:
			break;
		}
	}
	
	class BackLightCtrl implements Runnable{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			while (backLightRun) {
				if (backLightCount < backLightMax) {
					backLightCount++;
				}
				else {
					if (backLightIsOn) {
						backLightIsOn = false;
						handler.sendEmptyMessage(TurnOffBackLight);
					}
				}
				try {
					Thread.sleep(3000l);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}				
			}
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		backLightRun = false;
		unregisterReceiver(dynamicReceiver);
		cancelAllTimer();
		super.onDestroy();
	}
	
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "触摸");
		backLightCount = 0;
		if (!backLightIsOn) {
			backLightIsOn = true;
			handler.sendEmptyMessage(TurnOnBackLight);
		}
		return super.dispatchTouchEvent(ev);
	}



	@Override
	public boolean isBusy() {
		// TODO 自动生成的方法存根		
		return ScriptRun.getInstance().isRun();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		 InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);   
		    if (event.getAction() == MotionEvent.ACTION_DOWN) {    
		        if (MainActivity.this.getCurrentFocus() != null) {    
		              if (MainActivity.this.getCurrentFocus().getWindowToken() != null) {    
		                 imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),    
		                    InputMethodManager.HIDE_NOT_ALWAYS);    
		           }    
		       }    
		    }  
		return super.onTouchEvent(event);
	}

	@Override
	public void setDeviceTest() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();		
		ScriptChain chain = new ScriptChain("测量");				
		run.addChainOfScript(chain);
		run.startSchedule(tools.nowtime2timestamp(), "None");	
	}



	@Override
	public void setDeviceCalibration() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();		
		ScriptChain step1 = new ScriptChain("低点校准");	
		ScriptChain step2 = new ScriptChain("高点校准");
		run.addChainOfScript(step1);
		run.addChainOfScript(step2);
		run.startSchedule(tools.nowtime2timestamp(), "None");	
	}



	@Override
	public void setDeviceStop() {
		// TODO 自动生成的方法存根
		ScriptRun.getInstance().stopScriptRun();
	}



	@Override
	public void setDeviceTestStandard() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();		
		ScriptChain chain = new ScriptChain("测量标液");				
		run.addChainOfScript(chain);
		run.startSchedule(tools.nowtime2timestamp(), "None");
	}



	@Override
	public void setDeviceInit() {
		// TODO 自动生成的方法存根
		ScriptRun run = ScriptRun.getInstance();		
		ScriptChain chain = new ScriptChain("初始化");				
		run.addChainOfScript(chain);
		run.startSchedule(tools.nowtime2timestamp(), "None");
	}
	
	
	
	
}
