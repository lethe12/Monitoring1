package cn.com.grean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.com.grean.model.HistoryDataFormat;
import cn.com.grean.monitoring.MeasureInfoData;
import cn.com.grean.monitoring.MeasureView;
import cn.com.grean.protocol.ProtocolProcessorImp;
import cn.com.grean.script.ErrorFormat;
import cn.com.grean.script.LogFormat;
import cn.com.grean.script.ResultDataFormat;
import cn.com.grean.script.ScriptGhostListener;
import cn.com.grean.script.ScriptReader;
import cn.com.grean.script.WarningInfoListener;
import cn.com.grean.script.WarningManager;
import cn.com.grean.script.algorithm.AbsorbanceComputeData;
import cn.com.grean.script.algorithm.AbsorbancyMultiSampleComputeData;
import cn.com.grean.script.algorithm.Compute;
import cn.com.grean.script.algorithm.ComputerDirector;
import cn.com.grean.script.algorithm.ComputerListener;
import cn.com.grean.script.algorithm.ComputerParams;
import cn.com.grean.script.algorithm.DualAbsCompute;
import cn.com.grean.script.algorithm.DualWaveCompute;
import cn.com.grean.script.algorithm.GeneralComputerParams;
import cn.com.grean.script.algorithm.MultiSampleComputer;
import cn.com.grean.script.algorithm.TitrationComputeData;
import cn.com.grean.script.instruction.GeneralData;
import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class myApplication extends Application implements Observer,ScriptGhostListener,ScriptReader,ComputerListener,WarningInfoListener,LocalConfig{
	private final static String tag = "myApplication"; 
	@SuppressWarnings("unused")
	private RS232 rs232;
	private RS485 rs485;
	private static myApplication instance;//单例化
	private Compute compute=null;
    private MultiSampleComputer multiSampleComputer = null;
	private DualWaveCompute dualCompute = null;
	private EquipmentInfo equipmentInfo;	
	private HashMap<String, Object> config= new HashMap<String, Object>();//存储配置参数
	private MeasureView measureView;
	private String devicesName="";
	private MeasureInfoData measureInfoData;
	private float lastResult;
    private float[] lastResults;
	private long lastDate;	
	private int devicesRange = 0,sampleNumber = 1;

	public String getDevicesName() {
		return devicesName;
	}
	
	public float getLastResult() {
		return lastResult;
	}

	public long getLastDate() {
		return lastDate;
	}

	public void setMeasureView(MeasureView view){
		measureView = view;
	}
	
	public HashMap<String, Object> getConfig() {
		return config;
	}
	
	public myApplication() {
		// TODO 自动生成的构造函数存根
		rs232 = RS232.getInstance();
		rs485 = RS485.getInstance();
		//getCompute();
		//LoadConfig();
		
	}
	public static myApplication getInstance() {
		return instance;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onCreate() {
		// TODO 自动生成的方法存根
		super.onCreate();
		this.instance = this;
		LoadConfig();
	}
	/**
	 * 导入整张表，内容为字符串，主键自增
	 * 
	 * 
	 * @param tablename
	 * 表名
	 * 
	 * @param list
	 * 输入的字符串内容
	 */
	public void updataDataBase(String tablename,ArrayList<String> list){
		DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
		SQLiteDatabase db =  helperDbTask.getWritableDatabase();
		String cmd = "DROP TABLE "+tablename;
		db.execSQL(cmd);
		cmd = "CREATE TABLE "+tablename+" ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)";
		db.execSQL(cmd);
		for(int i=0;i<list.size();i++){
			ContentValues values = new ContentValues();
			values.put("content", list.get(i));
			long l =db.insert(tablename, null, values);
			if(l==-1){
				Log.d(tag,"数据库写失败");
			}
		}
		db.close();
	}
	/**
	 * 从数据库读文本信息
	 * @param tablename 表名
	 * @return 字符串数组
	 */
	public String [] readDataBase(String tablename) {
		Cursor c;
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		String cmdString = "SELECT * FROM "+tablename+" WHERE id > ?";
		c = db.rawQuery(cmdString, new String[]{"0"});
		String [] list = new String[c.getCount()];
		int i=0;
		while(c.moveToNext()){
			list[i] =String.valueOf(i)+":"+ c.getString(c.getColumnIndex("content"));
			i++;			
		}
		c.close();
		db.close();
		helperDbTask.close();
		return list;
	}
	/**
	 * 查询历史数据
	 * @param statement spl条件语句
	 * @return
	 */
	public HistoryDataFormat loadHistoryData(String statement){
		HistoryDataFormat format = new HistoryDataFormat();
		DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("SELECT * FROM result WHERE "+statement + " ORDER BY date desc", new String[]{});
		int i=0;
		while (c.moveToNext()) {
			format.getMemo().add(c.getString(0));
			format.getData().add(c.getFloat(1));
			format.getDate().add(c.getLong(2));
			i++;
		}
		/*
		 * */
		format.setCount(i);
		/*Log.d(tag, "开始仿真加载历史数据");
		format.setCount(2);
		format.getMemo().add("第一条数据");
		format.getData().add(10f);
		format.getDate().add(123456l);
		format.getMemo().add("第二条数据");
		format.getData().add(20f);
		format.getDate().add(1234560l);*/
		db.close();
		helperDbTask.close();
		return format;
	}
	
	/**
	 * 读取数据库中的文本信息
	 * @param tableName 表名
	 * @param statement 条件
	 * @param textList 需要装入的List
	 * @param index 表中文本坐在地列
	 */
	public void loadDataBase(String tableName, String statement,List<String> textList,int index,int dateIndex){
		DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		c = db.rawQuery("SELECT * FROM "+tableName+" WHERE "+statement + " ORDER BY date desc", new String[]{});
		String string;
		while (c.moveToNext()) {
			string = tools.timestamp2string(c.getLong(dateIndex))+c.getString(index);
			//Log.d(tag, string);
			textList.add(string);
		}
		c.close();
		db.close();
		helperDbTask.close();
	}	
	
	public void loadLastResult() {
		DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from result order by date desc", new String[]{});//这边写上你的查询语句
		if (cursor.moveToNext()) {
			lastResult =cursor.getFloat(1);
			lastDate = cursor.getLong(2);
		}
		else {
			lastResult = 0f;
			lastDate = 0;
		}
		ProtocolProcessorImp.getInstance().setProtocolResult(lastResult);
		ProtocolProcessorImp.getInstance().setProtocolTimeStamp(lastDate);
	}

	public void loadLastResult(int sampleNumber){
        lastResults = new float[sampleNumber];
        int index = 0;
        lastDate = 0;
        DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
        SQLiteDatabase db =  helperDbTask.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from result order by date desc", new String[]{});//这边写上你的查询语句
        while (cursor.moveToNext()) {
            lastResults[index] =cursor.getFloat(1);
            lastDate = cursor.getLong(2);
            index++;
        }
        if(index < (sampleNumber-1)){
            for(int i=index;i<sampleNumber;i++){
                lastResults[i] = 0f;
            }
        }
        lastResult = lastResults[0];
        ProtocolProcessorImp.getInstance().setProtocolResult(lastResult);
        ProtocolProcessorImp.getInstance().setProtocolTimeStamp(lastDate);

    }
	
	public void dropDataBase (String [] tables){
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].equals("result")) {
				db.execSQL(DbTask.RESULT_DROP);
				db.execSQL(DbTask.RESULT_CREATE);
			}
			else if (tables[i].equals("log")) {
				db.execSQL(DbTask.LOG_DROP);
				db.execSQL(DbTask.LOG_CREATE);
			}
			else if (tables[i].equals("tempdata")) {
				db.execSQL(DbTask.TEMPDATA_DROP);
				db.execSQL(DbTask.TEMPDATA_CREATE);
			}			
		}
		db.close();
		helperDbTask.close();
	}
	
	
	/*导出日志至u盘
	 * */
	public List<String> ExprotLog(){
		List<String> list = new ArrayList<String>();
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		String string;

		//Log.d("数据库查询", string);
		c = db.rawQuery("SELECT * FROM log ORDER BY date desc", new String[]{});
		String dd,vol;
		long l;
		while(c.moveToNext()){
			string = tools.timestamp2string(c.getLong(1))+" "+c.getString(2);
			list.add(string);
		}
		
		c.close();
		db.close();
		helperDbTask.close();
		return list;
	}

	/*导出详细数据
	 * */
	public List<String> ExprotTempData(){
		List<String> list = new ArrayList<String>();
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		String string;

		//Log.d("数据库查询", string);
		c = db.rawQuery("SELECT * FROM allTempdata ORDER BY date desc", new String[]{});
		String dd,vol;
		while(c.moveToNext()){
			//Log.d("数据查询", c.getString(0)+""+c.getInt(3));
			dd=tools.timestamp2StringSecond(c.getLong(0));
			vol=String.valueOf(c.getFloat(1));
			string = dd+" CH1:"+vol;
			vol=String.valueOf(c.getFloat(2));
			string += " CH2:"+vol;
			vol=String.valueOf(c.getFloat(3));
			string += " CH3:"+vol;
			vol=String.valueOf(c.getFloat(4));
			string += " CH4:"+vol;
			vol=String.valueOf(c.getFloat(5));
			string += " CH5:"+vol;
			vol=String.valueOf(c.getFloat(6));
			string += " CH6:"+vol;
			vol=String.valueOf(c.getFloat(7));
			string += " CH7:"+vol;
			vol=String.valueOf(c.getFloat(8));
			string += " CH8:"+vol;
			vol=String.valueOf(c.getFloat(9));
			string += " CH9:"+vol;
			list.add(string);
			//Log.d("详细数据", string);
		}		
		
		c.close();
		db.close();
		helperDbTask.close();
		return list;
	}
	/*导出数据至u盘
	 * */
	public List<String> ExprotData(){
		List<String> list = new ArrayList<String>();
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		String string;
		//Log.d("数据库查询", string);
		c = db.rawQuery("SELECT * FROM result ORDER BY date desc", new String[]{});
		while(c.moveToNext()){			
			string = tools.timestamp2string(c.getLong(2))+" "+tools.float2String3(c.getFloat(1));
			Log.d(tag, string);			
			list.add(string);			
		}		
		c.close();
		db.close();
		helperDbTask.close();
		return list;
	}
	/**
	 * 查看当前是否正在维护
	 * @return true 正在维护
	 */
	public boolean isMaintenaning(){
		return !rs485.isCommunicateEnable();
	}
	/**
	 * 设置维护状态
	 * @param key true为维护状态 不可通讯
	 */
	public void setMaintance(boolean key){
		rs485.setCommunicateEnable(!key);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO 自动生成的方法存根
		
		Log.d(tag, "被观察者"+observable.toString()+"日志记录类型:"+data.toString());
		if (observable.toString().equals("ScriptContent")) {			
			DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
			SQLiteDatabase db =  helperDbTask.getReadableDatabase();
			ContentValues values = new ContentValues();
			if (data.toString().equals("LogFormat")) {	
				LogFormat log = (LogFormat) data;
				values.put("date", log.getDate());
				values.put("content", log.getText());
				db.insert("log", null, values);		
			}
			else if (data.toString().equals("ResultDataFormat")) {
				ResultDataFormat res = (ResultDataFormat)data;
				values.put("memo",res.getText());//记录信息
				values.put("date", res.getDate());//存当前测量的时间戳
				values.put("result", res.getResult());//存计算结果
				long l =db.insert("result", null, values);
				if(l==-1){
					Log.d("数据","失败");
				}
				putConfig("CurrentResult", res.getResult());
				putConfig("TestInfo", res.getText());
				WarningManager.getInstance().warningOverLimit(res.getResult(), this);
			}	
			else if (data.toString().equals("ErrorFormat")) {
				ErrorFormat err = (ErrorFormat)data;
				values.put("date", err.getDate());
				values.put("content", err.getText());
				db.insert("log", null, values);	
				putConfig("ErrorString", err.getText()+"\n");
				if (measureView!=null) {
					measureView.showErrorICON(true);
				}
			}
			db.close();
			helperDbTask.close();
		}
		
	}
	
	
	
	private void LoadConfig(){
		SharedPreferences.Editor editor;
		// 配置文件
		Log.d(tag, "初始化");
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		editor = sp.edit();
		if (sp.getBoolean("FactorySettings", false) == false) {//第一次写配置文件，写入默认配置
			editor.putBoolean("FactorySettings", true);
			editor.putString("DevicesName", "TN");
			editor.putString("TCPIP", "192.168.168.134");
			editor.putInt("TCPPORT", 12803);
			//editor.putStringSet(arg0, arg1)
			editor.commit();
		}
		devicesName = sp.getString("DevicesName", "TN");
		devicesRange = sp.getInt("DevicesRange", 0);
        sampleNumber = sp.getInt("SampleNumber",1);
		
		ProtocolProcessorImp.getInstance().setByteProtocolAddress((byte) sp.getInt("SLaveAddress", 1));
		ProtocolProcessorImp.getInstance().setASCIIProtocolID(sp.getString("TCPID", "12345"));
		WarningManager.getInstance().loadRangevalue(this);
	}
	/**
	 * 记录幽灵进程的数据库命令
	 */
	@Override
	public void RecordGhost(GeneralData data) {
		// TODO 自动生成的方法存根
		//Log.d(tag, "记录数据");
		if (data!=null) {
			DbTask helperDbTask = new DbTask(getApplicationContext(), 2);
			SQLiteDatabase db = helperDbTask.getReadableDatabase();
			ContentValues values = new ContentValues();
			long l = tools.nowtime2timestamp();
			values.put("date", l);
			values.put("ch1", data.getOne(0));
			values.put("ch2", data.getOne(1));
			values.put("ch3", data.getOne(2));
			values.put("ch4", data.getOne(3));
			values.put("ch5", data.getOne(4));
			values.put("ch6", data.getOne(5));
			values.put("ch7", data.getOne(6));
			values.put("ch8", data.getOne(7));
			values.put("ch9", data.getOne(8));
			db.insert("allTempdata", null, values);
			db.close();
			helperDbTask.close();
		}
	}
	
	public long getConfigLong(String key){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		return sp.getLong(key, 3600000l);
	}
	
	public String getConfigString(String key){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		return sp.getString(key, "");
	}
	
	public float getConfigFloat(String key) {
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		return sp.getFloat(key, 0f);
	}
	
	public boolean getConfigBoolean(String key){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
	
	public int getConfigInt(String key){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	
	public void putConfig(String key,String value){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor;
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void putConfig(String key,float value){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor;
		editor = sp.edit();
		editor.putFloat(key, value);
		editor.commit();
	}
	
	public void putConfig(String key,long value){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor;
		editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public void putConfig(String key,boolean value){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor;
		editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public void putConfig(String key,int value){
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		SharedPreferences.Editor editor;
		editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public Compute getCompute(){
		if (compute == null){
			compute = SelectComputer();			
		}
		return compute;
	}

	public MultiSampleComputer getMultiSampleComputer(){
        if(multiSampleComputer == null){
            multiSampleComputer =selectMultiSampleComputer();
        }
        return multiSampleComputer;
    }
	
	public DualWaveCompute getDualCompute() {
		if ((dualCompute == null)||(compute == null)) {
			compute = SelectComputer();
		}
		return dualCompute;
	}
	
	public EquipmentInfo getEquipmentInfo(){
		//Log.d(tag, "name="+devicesName);
		if (equipmentInfo == null) {			
			if (devicesName.equals("TN")) {
				equipmentInfo = new TnEquipmentInfo();

			}else if (devicesName.equals("Glyphosate")) {
				equipmentInfo = new GlyphosateEquipmentInfo();
			}else if(devicesName.equals("TP")){
				equipmentInfo = new TpEquipmentInfo();
			}else if(devicesName.equals("NH4")){
				equipmentInfo = new Nh4EquipmentInfo();
			}else if(devicesName.equals("Microcystis")) {
				equipmentInfo = new MicrocystisEquipmentInfo();
			}
			else {
				equipmentInfo = new TnEquipmentInfo();
			}
		}
		return equipmentInfo;

	}

	private MultiSampleComputer selectMultiSampleComputer(){
        // 配置文件
        SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
        ComputerDirector director = new ComputerDirector(new GeneralComputerParams());
        ComputerParams params = director.construct(sp);
        if(devicesName.equals("Glyphosate")||devicesName.equals("Microcystis")){
            return new AbsorbancyMultiSampleComputeData(params,this);
        }else{
            return new AbsorbancyMultiSampleComputeData(params,this);
        }

    }
	
	private Compute SelectComputer(){

		// 配置文件
		SharedPreferences sp = this.getSharedPreferences("config",MODE_PRIVATE);
		Log.d(tag, "初次读取校准参数");

		/*float slope = sp.getFloat("slope", 1f);
		float intercept = sp.getFloat("intercept", 0f);
		float slopeMax = sp.getFloat("slopeMax", 100f);
		float slopeMin = sp.getFloat("slopeMin", 0f);
		float interceptMax = sp.getFloat("interceptMax", 100f);
		float interceptMin = sp.getFloat("interceptMin", -100f);
		float lastValueLow = sp.getFloat("lastValueLow", 0.001f);
		float lastValueHigh = sp.getFloat("lastValueHigh", 1f);
		float lastTargetLow = sp.getFloat("lastTargetLow", 0f);
		float lastTargetHigh = sp.getFloat("lastTargetHigh", 0.5f);
		float consumerSlope = sp.getFloat("ConsumerSlope", 1f);
		float consumerIntercept = sp.getFloat("ConsumerIntercept", 0f);
		float currentResult = sp.getFloat("CurrentResult", 0.0001f);*/
		
		ComputerDirector director = new ComputerDirector(new GeneralComputerParams());
		ComputerParams params = director.construct(sp);
		
		//Log.d(tag, String.valueOf(slopeMin)+";"+String.valueOf(slopeMax)+";"+String.valueOf(interceptMin)+";"+String.valueOf(interceptMax));
		if (devicesName.equals("TN")) {	
			DualAbsCompute dualAbsCompute =  new DualAbsCompute(params,this);
			dualCompute = dualAbsCompute;
			return dualAbsCompute;	
			
			//return new AbsorbanceComputeData(params,this);
		}else if(devicesName.equals("CODMN")){
			return new TitrationComputeData(params,this);
		}else if (devicesName.equals("NH4GSE")) {
			return new TitrationComputeData(params,this);
		}else if (devicesName.equals("TNDual")) {
			//return new DualAbsCompute(slope, intercept, slopeMax, slopeMin, interceptMax, interceptMin, lastValueLow, lastValueHigh, 
			//lastTargetLow, lastTargetHigh, consumerSlope, consumerIntercept, currentResult);
			DualAbsCompute dualAbsCompute =  new DualAbsCompute(params,this);
			dualCompute = dualAbsCompute;
			return dualAbsCompute;			 
		}else if (devicesName.equals("CLDPD")) {
			return new AbsorbanceComputeData(params, this);
		}else {
			return new AbsorbanceComputeData(params,this);
		}
	}
	/**
	 * 获取本仪器参数名称
	 * @return
	 */
	public String getParameterName (){
		if (devicesName.equals("TN")) {
			return "总氮";
		}
		else  if(devicesName.equals("TP")){
			 return "总磷";
		}
		else if(devicesName.equals("NH4")){
			return "氨氮";
		}
		else if (devicesName.equals("Glyphosate")) {
			return "草甘膦";
		}else if(devicesName.equals("Microcystis")){
			return "微囊藻毒素";
		}else {
			return "NONE";
		}
	}

	@Override
	public void loadScriptContent(String name, LinkedList<String> list) {
		// TODO 自动生成的方法存根
		
		
		list.clear();
		DbTask helperDbTask = new DbTask(getApplicationContext(),2);
		SQLiteDatabase db =  helperDbTask.getReadableDatabase();
		Cursor c;
		String string,tableName;
		//Log.d("数据库查询", string);
		if (name.equals("测量")) {
			if ((devicesRange>0)&&(devicesRange<8)) {
				tableName = "measurescript"+String.valueOf(devicesRange+1);
			}
			else {
				tableName = "measurescript";
			}
		}
		else if (name.equals("高点校准")) {
			if ((devicesRange>0)&&(devicesRange<8)){
				tableName="hcalibratscript"+String.valueOf(devicesRange+1);
			}else {
				
				tableName="hcalibratscript";
			}
		}
		else if (name.equals("低点校准")) {
			if ((devicesRange>0)&&(devicesRange<8)){
				tableName="lcalibratscript"+String.valueOf(devicesRange+1);
			}else {
				tableName="lcalibratscript";				
			}
			
		}
		else if (name.equals("初始化")) {
			tableName="initscript";
		}
		else if (name.equals("维护")) {
			tableName="vindicatescript";
		}
		else if(name.equals("测量标液")){
			if ((devicesRange>0)&&(devicesRange<8)){
				tableName = "qualityscript"+String.valueOf(devicesRange+1);
			}else {
				
				tableName = "qualityscript";
			}
		}
		else if (name.equals("清洗")) {
			tableName = "clearscript"; 
		}
		else{
			tableName = "script";
		}
		
		c = db.rawQuery("SELECT * FROM "+tableName+" WHERE id > ?", new String[]{"0"});
		while(c.moveToNext()){			
			string = c.getString(1);
			//Log.d(tag, string);			
			list.add(string);			
		}		
		c.close();
		db.close();
		helperDbTask.close();
	}
	/**
	 * 获取当前测量界面下内容
	 * @return
	 */
	public MeasureInfoData getMeasureInfoData() {
		return measureInfoData;
	}
	/**
	 * 存储当前测量界面下显示内容
	 * @param measureInfoData
	 */
	public void setMeasureInfoData(MeasureInfoData measureInfoData) {
		this.measureInfoData = measureInfoData;
	}

	@Override
	public void saveData(String tag, float data) {
		// TODO 自动生成的方法存根
		putConfig(tag, data);
	}

	@Override
	public String getWarringString() {
		// TODO 自动生成的方法存根
		return getConfigString("WarningString");
	}

	@Override
	public String getErrorString() {
		// TODO 自动生成的方法存根
		return getConfigString("ErrorString");
	}

	@Override
	public void saveWarringString(String string) {
		// TODO 自动生成的方法存根
		putConfig("WarningString", string);
	}

	@Override
	public void saveErrorString(String string) {
		// TODO 自动生成的方法存根
		
		putConfig("ErrorString", string);
	}

	@Override
	public void saveRangeValue(float floor, float upper) {
		// TODO 自动生成的方法存根
		putConfig("WarningFloor", floor);
		putConfig("WarningUpper", upper);
	}

	@Override
	public float getFloorValue() {
		// TODO 自动生成的方法存根
		return getConfigFloat("WarningFloor");
	}

	@Override
	public float getUpperValue() {
		// TODO 自动生成的方法存根
		return getConfigFloat("WarningUpper");
	}

	@Override
	public boolean showWarningICON(boolean key) {
		// TODO 自动生成的方法存根
		if (measureView == null) {
			
			return false;
		}else {
			measureView.showWarningICON(key);
			return true;
		}
	}

	@Override
	public boolean showErrorICON(boolean key) {
		// TODO 自动生成的方法存根
		if (measureView == null) {			
			return false;
		}
		else {
			measureView.showErrorICON(key);
			return true;
		}
	}

	public int getDevicesRange() {
		return devicesRange;
	}

	public int getSampleNumber(){return sampleNumber;}

	public void setDevicesRange(int devicesRange) {
        sampleNumber = equipmentInfo.getSampleNumber(devicesRange);
        putConfig("SampleNumber",sampleNumber);
		putConfig("DevicesRange", devicesRange);
		this.devicesRange = devicesRange;
	}

    public float[] getLastResults() {
        return lastResults;
    }
}
