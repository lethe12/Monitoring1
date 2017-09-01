package cn.com.grean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbTask extends SQLiteOpenHelper{
	private final static String tag="DbTask";
	public final static int RESULT_MEMO = 0;
	public final static int LOG_CONTENT = 2;
	public final static int RESULT_DATE = 2;
	public final static int LOG_DATE = 1;
	public final static String LOG_DROP = "DROP TABLE log";
	public final static String LOG_CREATE ="CREATE TABLE log ( id INTEGER PRIMARY KEY AUTOINCREMENT,date LONG, content TEXT)";
	public final static String RESULT_DROP = "DROP TABLE result";
	public final static String RESULT_CREATE = "CREATE TABLE result ( memo TEXT, result FLOAT , date INTEGET)";
	public final static String TEMPDATA_DROP = "DROP TABLE allTempdata";
	public final static String TEMPDATA_CREATE = "CREATE TABLE allTempdata(date INTEGET,ch1 FLOAT,ch2 FLOAT,ch3 FLOAT,ch4 FLOAT,ch5 FLOAT,ch6 FLOAT,ch7 FLOAT,ch8 FLOAT,ch9 FLOAT)";
	
	public DbTask(Context context, int version) {
		//固定使用 data。db数据库
		super(context, "data.db", null, version);
		// TODO 自动生成的构造函数存根
	}
/*
 * 创建时调用
 * */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		db.execSQL("CREATE TABLE script ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//默认脚本
		db.execSQL("CREATE TABLE result ( memo TEXT, result FLOAT , date INTEGET)");//存储数据 日期，备忘录，
		db.execSQL("CREATE TABLE measurescript ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("CREATE TABLE initscript ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//初始化脚本
		db.execSQL("CREATE TABLE vindicatescript ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//维护脚本
		db.execSQL("CREATE TABLE log ( id INTEGER PRIMARY KEY AUTOINCREMENT,date LONG, content TEXT)");//日志
		db.execSQL("CREATE TABLE qualityscript(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("CREATE TABLE clearscript(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//清洗脚本
		db.execSQL("CREATE TABLE allTempdata(date INTEGET,ch1 FLOAT,ch2 FLOAT,ch3 FLOAT,ch4 FLOAT,ch5 FLOAT,ch6 FLOAT,ch7 FLOAT,ch8 FLOAT,ch9 FLOAT)");
		db.execSQL("CREATE TABLE tempdata(date INTEGET,data FLOAT)");//记录数据

		db.execSQL("insert into measurescript values(1,'Starttest,1')");
		db.execSQL("insert into measurescript values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript values(2,'Endtest')");
		db.execSQL("insert into initscript values(1,'Starttest,1')");
		db.execSQL("insert into initscript values(2,'Endtest')");
		db.execSQL("insert into vindicatescript values(1,'Starttest,1')");
		db.execSQL("insert into vindicatescript values(2,'Endtest')");
		db.execSQL("insert into qualityscript values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript values(2,'Endtest')");
		db.execSQL("insert into clearscript values(1,'Starttest,1')");
		db.execSQL("insert into clearscript values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript2 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript2 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript2 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript2 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript2 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript2 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript3 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript3 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript3 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript3 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript3 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript3 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript4 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript4 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript4 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript4 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript4 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript4 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript5 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript5 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript5 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript5 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript5 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript5 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript6 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript6 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript6 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript6 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript6 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript6 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript7 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript7 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript7 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript7 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript7 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript7 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE measurescript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
		db.execSQL("CREATE TABLE hcalibratscript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
		db.execSQL("CREATE TABLE lcalibratscript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
		db.execSQL("insert into measurescript8 values(1,'Starttest,1')");
		db.execSQL("insert into measurescript8 values(2,'Endtest')");
		db.execSQL("insert into hcalibratscript8 values(1,'Starttest,1')");
		db.execSQL("insert into hcalibratscript8 values(2,'Endtest')");
		db.execSQL("insert into lcalibratscript8 values(1,'Starttest,1')");
		db.execSQL("insert into lcalibratscript8 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript2(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript2 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript2 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript3(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript3 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript3 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript4(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript4 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript4 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript5(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript5 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript5 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript6(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript6 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript6 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript7(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript7 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript7 values(2,'Endtest')");
		
		db.execSQL("CREATE TABLE qualityscript8(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
		db.execSQL("insert into qualityscript8 values(1,'Starttest,1')");
		db.execSQL("insert into qualityscript8 values(2,'Endtest')");
		
	}
/*
 * s数据库更新时调用
 * */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根
		Log.d(tag, String.valueOf(oldVersion)+";"+String.valueOf(newVersion));
		switch (oldVersion) {
		case 1:
			db.execSQL("CREATE TABLE measurescript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript2 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript2 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript2 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript2 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript2 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript2 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript2 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript3 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript3 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript3 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript3 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript3 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript3 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript3 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript4 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript4 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript4 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript4 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript4 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript4 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript4 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript5 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript5 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript5 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript5 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript5 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript5 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript5 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript6 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript6 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript6 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript6 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript6 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript6 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript6 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript7 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript7 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript7 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript7 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript7 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript7 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript7 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE measurescript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//测量脚本
			db.execSQL("CREATE TABLE hcalibratscript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//高点脚本
			db.execSQL("CREATE TABLE lcalibratscript8 ( id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//低点脚本
			db.execSQL("insert into measurescript8 values(1,'Starttest,1')");
			db.execSQL("insert into measurescript8 values(2,'Endtest')");
			db.execSQL("insert into hcalibratscript8 values(1,'Starttest,1')");
			db.execSQL("insert into hcalibratscript8 values(2,'Endtest')");
			db.execSQL("insert into lcalibratscript8 values(1,'Starttest,1')");
			db.execSQL("insert into lcalibratscript8 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript2(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript2 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript2 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript3(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript3 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript3 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript4(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript4 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript4 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript5(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript5 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript5 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript6(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript6 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript6 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript7(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript7 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript7 values(2,'Endtest')");
			
			db.execSQL("CREATE TABLE qualityscript8(id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT)");//质控样脚本
			db.execSQL("insert into qualityscript8 values(1,'Starttest,1')");
			db.execSQL("insert into qualityscript8 values(2,'Endtest')");
			break;

		default:
			break;
		}
	}

}
