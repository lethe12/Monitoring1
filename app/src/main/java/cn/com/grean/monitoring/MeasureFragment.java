package cn.com.grean.monitoring;

import cn.com.grean.myApplication;
import cn.com.grean.Presenter.MeasurePresenter;
import cn.com.grean.Presenter.MeasurePresenterCompl;
import cn.com.grean.model.MeasureInfo;
import cn.com.grean.script.ScriptRun;
import android.R.integer;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MeasureFragment extends Fragment implements MeasureView,OnClickListener {
	private final static String tag = "MeasureFragment";
	private static boolean hasLoaded = false;
	private ProgressDialog pd;  
	
	private final static int ScriptCommandInfo = 1;
	private final static int ScriptResultInfo = 2;
	private final static int ShowWarringICON = 3;
	private final static int ShowOffWarringICON = 4;
	private final static int ShowErrorICON = 5;
	private final static int ShowOffErrotICON = 6;
	private final static int ShowOnlineICON = 7;
	private final static int ShowOffOnlineICON = 8;
	private final static int ShowMaintanceICON = 9;
	private final static int ShowOffMaintanceICON = 10;
	//private Button btn_testButton;
	private TextView tv_result;
	private TextView tv_date;
	private MeasurePresenter measurePresenter;
	private TextView tv_measure_info;
	private ProgressBar pb_measure;
	private TextView tv_name;
	private TextView tv_nextTest;
	private TextView tv_nextCal;
	private ImageView iv_logo;
	private ImageView iv_error;
	private ImageView iv_warning;
	private ImageView iv_online;
	private ImageView iv_maintance;
	private MeasureInfoFactory factory;
	
	private int Max,Progress;
	private String info,result,time;
	private int logoClickTimes=0;//点击6次可查看软件版本
	
	Handler handlerDownload = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DownloadManager.STATUS_SUCCESSFUL:
                    //downloadDialog.setProgress(100);
                	pd.setProgress(100);
                    canceledDialog();
                    Toast.makeText(getActivity(), "下载任务已经完成！", Toast.LENGTH_SHORT).show();
                    break;

                case DownloadManager.STATUS_RUNNING:
                    //int progress = (int) msg.obj;
                    //downloadDialog.setProgress((Integer) msg.obj);
                    //canceledDialog();
                	//pd.setProgress((Integer)msg.obj);
                    break;

                case DownloadManager.STATUS_FAILED:
                    canceledDialog();
                    break;

                case DownloadManager.STATUS_PENDING:
                	
                    showDialog();
                    break;
                case DownloadManager.STATUS_PAUSED:
                	Toast.makeText(getActivity(), "下载失败！", Toast.LENGTH_SHORT).show();
                	canceledDialog();
                	break;
            }
        }
    };
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ScriptCommandInfo:
				pb_measure.setMax(Max);
				pb_measure.setProgress(Progress);
				if (info != null) {	
					tv_measure_info.setText("当前状态:"+info);
				}
				factory.setCount(Progress);
				factory.setMax(Max);
				factory.setInfo(info);
				myApplication.getInstance().setMeasureInfoData(factory.build());
				break;
			case ScriptResultInfo:
				tv_result.setText(result);
				tv_date.setText("测量时间:"+time);
				break;
			case ShowErrorICON:
				iv_error.setVisibility(View.VISIBLE);
				break;
			case ShowOffErrotICON:
				iv_error.setVisibility(View.INVISIBLE);
				break;
			case ShowMaintanceICON:
				iv_maintance.setVisibility(View.VISIBLE);
				break;
			case ShowOffMaintanceICON:
				iv_maintance.setVisibility(View.INVISIBLE);
				break;
			case ShowOnlineICON:
				iv_online.setVisibility(View.VISIBLE);
				Toast.makeText(getActivity(), "已连接服务器", Toast.LENGTH_SHORT).show();
				break;
			case ShowOffOnlineICON:
				iv_online.setVisibility(View.INVISIBLE);
				Toast.makeText(getActivity(), "已断开服务器", Toast.LENGTH_SHORT).show();
				break;
			case ShowWarringICON:
				iv_warning.setVisibility(View.VISIBLE);
				break;
			case ShowOffWarringICON:
				iv_warning.setVisibility(View.INVISIBLE);
				
			default:
				break;
			}
			
			
		};
	};
	
    public void download(){
    	showDialog();
        //最好是用单线程池，或者intentService取代
        new Thread(new DownLoadRunnable(getActivity(),measurePresenter.getUpdataURL(), handlerDownload)).start();
    }
    
	public MeasureFragment() {
		// TODO 自动生成的构造函数存根
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		//return super.onCreateView(inflater, container, savedInstanceState);
		View messageLayout = inflater.inflate(R.layout.activity_measure, container, false);  
		initView(messageLayout);
		
		/*btn_testButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根

				ScriptRun run = ScriptRun.getInstance();
				synchronized (MeasureFragment.this) {					
					Log.d(tag, "测试");
					//run.startScript("测量",tools.nowtime2timestamp());
					ScriptChain first = new ScriptChain("低点校准");
					ScriptChain second = new ScriptChain("高点校准");
					ScriptChain third = new ScriptChain("测量");
					if (!run.isRun()) {					
						run.addChainOfScript(first);
						run.addChainOfScript(second);
						run.addChainOfScript(third);
						run.startSchedule(tools.nowtime2timestamp(), "None");
					}
					else {
						Toast.makeText(getActivity(), "系统正忙", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});*/
		
		measurePresenter = new MeasurePresenterCompl(this);
		measurePresenter.fetch();
		ScriptRun.getInstance().setMeasureView(this);	
		
		IntentFilter filter_dynamic = new IntentFilter();  
        filter_dynamic.addAction("nextTest");  //更新下次测量时间
        filter_dynamic.addAction("nextCal");//更新下次自动校准时间
        filter_dynamic.addAction("maintanceSign");//维护信号
        filter_dynamic.addAction("connectTCP");
        getActivity().registerReceiver(dynamicReceiver, filter_dynamic);
        factory = new MeasureInfoFactory();
        hasLoaded = true;
        return messageLayout; 
	}
	
	private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			if (intent.getAction().equals("nextTest")) {
				//Log.d(tag, intent.getStringExtra("dateString"));
				tv_nextTest.setText(intent.getStringExtra("dateString"));
			}
			else if (intent.getAction().equals("nextCal")) {
				tv_nextCal.setText(intent.getStringExtra("dateString"));
			}
			else if (intent.getAction().equals("maintanceSign")) {
				if (intent.getBooleanExtra("key", false)) {
					handler.sendEmptyMessage(ShowMaintanceICON);
				}
				else {
					handler.sendEmptyMessage(ShowOffMaintanceICON);
				}
			}
			else if (intent.getAction().equals("connectTCP")) {
				if (intent.getBooleanExtra("key", false)) {
					handler.sendEmptyMessage(ShowOnlineICON);
				}
				else {
					handler.sendEmptyMessage(ShowOffOnlineICON);
				}
			}

		}
		
	};
	
	public void onDestroy() {
		Log.d(tag, "onDestroy");
		super.onDestroy();
		getActivity().unregisterReceiver(dynamicReceiver);
		
	};
	
	private void initView(View messageLayout){
		//btn_testButton=(Button) messageLayout.findViewById(R.id.btn_test);
		tv_date = (TextView) messageLayout.findViewById(R.id.tv_measure_date);
		tv_result=(TextView) messageLayout.findViewById(R.id.tv_measure_result);
		tv_measure_info=(TextView) messageLayout.findViewById(R.id.tv_measure_info);
		pb_measure = (ProgressBar) messageLayout.findViewById(R.id.pb_measure_progress);
		tv_name = (TextView) messageLayout.findViewById(R.id.tv_devices_name);
		tv_nextTest = (TextView) messageLayout.findViewById(R.id.tv_nexttest_time);
		tv_nextCal = (TextView) messageLayout.findViewById(R.id.tv_nextcal_time);
		iv_error = (ImageView) messageLayout.findViewById(R.id.ivBreakDown);
		iv_warning = (ImageView) messageLayout.findViewById(R.id.ivWarring);
		iv_online = (ImageView) messageLayout.findViewById(R.id.ivOnLine);
		iv_maintance = (ImageView) messageLayout.findViewById(R.id.ivMaintenance);
		iv_logo=(ImageView) messageLayout.findViewById(R.id.iv_logo);		
		iv_logo.setOnClickListener(this);
		pb_measure.setMax(100);
		pb_measure.setProgress(0);
		
	}

	@Override
	public void showMeasureInfo(MeasureInfo measureInfo) {
		// TODO 自动生成的方法存根
		tv_date.setText(measureInfo.getDateString());
		tv_result.setText(measureInfo.getResultString());
		tv_name.setText(measureInfo.getDevicesName());
		tv_nextTest.setText(measureInfo.getAutoTestDateString());
		tv_nextCal.setText(measureInfo.getAutoCalDateString());
		if (hasLoaded) {
			MeasureInfoData data = myApplication.getInstance().getMeasureInfoData();
			tv_measure_info.setText(data.getStateInfo());
			pb_measure.setMax(data.getProMax());
			pb_measure.setProgress(data.getProCount());
		}
		
	}

	@Override
	public void showCommandinfo(String info, int Progress, int Max) {		// TODO 自动生成的方法存根

		this.info = info;
		this.Progress=Progress;
		this.Max = Max;
		Message msg = new Message();
		msg.what = ScriptCommandInfo;
		handler.sendMessage(msg);		
	}

	@Override
	public void showResultInfo(String result, String time) {
		// TODO 自动生成的方法存根
		Log.d(tag, "显示结果");
		this.result = result;
		this.time = time;
		Message msg = new Message();
		msg.what = ScriptResultInfo;
		handler.sendMessage(msg);
	}

	@Override
	public void showErrorICON(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			handler.sendEmptyMessage(ShowErrorICON);
		}
		else {
			handler.sendEmptyMessage(ShowOffErrotICON);
		}
	}

	@Override
	public void showMaintainICON(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			handler.sendEmptyMessage(ShowMaintanceICON);
		}
		else {
			handler.sendEmptyMessage(ShowOffMaintanceICON);
		}
	}

	@Override
	public void showOnlineICON(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			handler.sendEmptyMessage(ShowOnlineICON);
		}
		else {
			handler.sendEmptyMessage(ShowOffOnlineICON);
		}
	}
	
	 private void showDialog() {
		 if (pd == null) {
			 pd= new ProgressDialog(getActivity());
			 pd.setMax(100);
			 pd.setTitle("提示");
			 pd.setMessage("系统更新中，其稍候..。");
			 pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			 pd.setCanceledOnTouchOutside(false);			
			// pd.show();
		}
		 
		if (!pd.isShowing()) {
			pd.show();
		}
		
    }
	 
	private void canceledDialog() {
		if (pd!=null&&pd.isShowing()) {
			pd.dismiss();
		}
    } 
	 
	 class DownLoadRunnable implements Runnable{
	    private String url;
	    private Handler handler;
	    private Context mContext;
	    
	    public  DownLoadRunnable(Context context, String url, Handler handler) {
			// TODO 自动生成的构造函数存根
	    	this.mContext = context;
	        this.url = url;
	        this.handler = handler;
	    }
		
	    private long startDownload() {
	        //获得DownloadManager对象
	        DownloadManager downloadManager=(DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
	        //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
	        Log.d(tag, "url="+url);
	        long requestId= downloadManager.enqueue(CreateRequest(url));
	        //查询下载信息方法
	        Log.d(tag, "requestId="+String.valueOf(requestId));
	        myApplication.getInstance().getConfig().put("ID", requestId);
	        queryDownloadProgress(requestId,downloadManager);
	        return  requestId;
	    }
	    
	    private void queryDownloadProgress(long requestId, DownloadManager downloadManager) {


	        DownloadManager.Query query=new DownloadManager.Query();
	        //根据任务编号id查询下载任务信息
	        query.setFilterById(requestId);
	        try {
	            boolean isGoging=true;
	            Log.d(tag, "下载状态");
	            int times = 0;
	            while (isGoging) {
	                Cursor cursor = downloadManager.query(query);
	                if (cursor != null && cursor.moveToFirst()) {

	                    //获得下载状态
	                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
	                   // Log.d(tag, "DownloadManager="+String.valueOf(state));
	                    switch (state) {
	                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
	                            isGoging=false;
	                            handler.obtainMessage(downloadManager.STATUS_SUCCESSFUL).sendToTarget();//发送到主线程，更新ui
	                            break;
	                        case DownloadManager.STATUS_FAILED://下载失败
	                            isGoging=false;
	                            handler.obtainMessage(downloadManager.STATUS_FAILED).sendToTarget();//发送到主线程，更新ui
	                            break;

	                        case DownloadManager.STATUS_RUNNING://下载中
	                            /**
	                             * 计算下载下载率；
	                             */
	                           /* int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
	                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
	                            Integer progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
	                            Log.d(tag, "running="+String.valueOf(totalSize)+"-"+String.valueOf(currentSize)+"-"+String.valueOf(progress));
	                            handler.obtainMessage(downloadManager.STATUS_RUNNING, progress).sendToTarget();//发送到主线程，更新ui*/
	                            break;

	                        case DownloadManager.STATUS_PAUSED://下载停止
	                        	times++;
	                        	if (times > 500) {
									isGoging = false;
	                        		handler.obtainMessage(DownloadManager.STATUS_PAUSED).sendToTarget();
								}
	                            break;

	                        case DownloadManager.STATUS_PENDING://准备下载
	                            handler.obtainMessage(DownloadManager.STATUS_PENDING).sendToTarget();
	                            break;
	                    }
	                    Thread.sleep(100);
	                }
	                if(cursor!=null){
	                    cursor.close();
	                }
	            }

	        }catch (Exception e){
	            e.printStackTrace();
	        }
	        Log.d(tag,"下载完成");
	    }

	    private DownloadManager.Request CreateRequest(String url) {

	        DownloadManager.Request  request=new DownloadManager.Request(Uri.parse(url));
	        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);// 隐藏notification

	        //request.setAllowedNetworkTypes(request.NETWORK_WIFI|request.NETWORK_MOBILE);//设置下载网络环境为wifi
	        Log.d(tag, Environment.getExternalStorageDirectory().toString());
	        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS,"456.apk");//指定apk缓存路径，默认是在SD卡中的Download文件夹
	        request.setDescription("杭州绿洁水务");
	        
	        return  request;
	    }
	    
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			//设置线程优先级为后台，这样当多个线程并发后很多无关紧要的线程分配的CPU时间将会减少，有利于主线程的处理   
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			
			//具体下载方法
	        startDownload();
		}
		 
	 }

	@Override
	public void showWarningICON(boolean key) {
		// TODO 自动生成的方法存根
		if (key) {
			handler.sendEmptyMessage(ShowWarringICON);
		}
		else {
			handler.sendEmptyMessage(ShowOffWarringICON);
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO 自动生成的方法存根
		super.onHiddenChanged(hidden);
		if (!hidden) {
			//Log.d(tag, "this = "+String.valueOf(isVisible()));
			if (!isVisible()) {
				//Log.d(tag, "this = "+String.valueOf(isAdded()));
				getActivity().getFragmentManager().beginTransaction().add(R.id.content, this).commit();
			}
		}
		else {
			//Log.d(tag, "hidden");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.iv_logo:
			logoClickTimes++;
			if (logoClickTimes >5) {
				logoClickTimes=0;
				new AlertDialog.Builder(getActivity()).setTitle("关于本机").setMessage(R.string.software_versions).
				setPositiveButton("升级", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						dialog.dismiss();
						download();
					}
				}).setNeutralButton("返回", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO 自动生成的方法存根
						dialog.dismiss();
					}
				}).show();
			}
			break;

		default:
			break;
		}
	}
}
