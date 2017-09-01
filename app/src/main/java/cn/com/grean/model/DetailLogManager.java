package cn.com.grean.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;
import cn.com.grean.myApplication;

public class DetailLogManager implements DetailLogManagerModel{

	public DetailLogManager() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void deleteAllData() {
		// TODO 自动生成的方法存根
		String [] tables ={"log","result","tempdata"};
		myApplication.getInstance().dropDataBase(tables);
		
	}

	@Override
	public boolean exportAllData() {
		// TODO 自动生成的方法存根
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			//Log.d("TestFile","SD card is not avaiable/writeable right now.");
			//return "未找到文件路径";
			return false;
		}
		//Toast.makeText(historyActivity.this,"开始导出", Toast.LENGTH_SHORT).show();
		//Log.e("SD路径", getSDPath());
		try {
			
			List<String>list = new ArrayList<String>();
			String pathName = "/storage/udisk1/disk-1/GREAN/"; // /storage/sdcard0/GREAN/
			String fileName = "日志.txt";
			String fileName1 = "数据.txt";
			String fileName2 = "详细数据.txt";
			File path = new File(pathName);
			File file = new File(pathName + fileName);
			File file1 = new File(pathName + fileName1);
			File file2 = new File(pathName + fileName2);
			if (!path.exists()) {
				Log.d("TestFile", "Create the path:" + pathName);
				path.mkdir();
			}
			if (!file.exists()) {
				Log.d("TestFile", "Create the file:" + fileName);
				file.createNewFile();
			}
			if (!file1.exists()) {
				Log.d("TestFile", "Create the file:" + fileName1);
				file1.createNewFile();
			}
			if (!file2.exists()) {
				Log.d("TestFile", "Create the file:" + fileName2);
				file2.createNewFile();
			}

			// 导出日志
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,false)); // true// 是添加在后面// false// 是每次写新的
			bw.write("系统日志 \r\n");
			
			list = myApplication.getInstance().ExprotLog();
			for (String tmp : list) {
				bw.write(tmp + "\r\n");
				//Log.d("写入SD", tmp);
			}
			bw.flush();
			bw.close();

			// 导出数据
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(file1, false)); // true// 是添加在后面// false// 是每次写新的
			bw1.write("测量结果  \r\n");
			list = myApplication.getInstance().ExprotData();
			for (String tmp : list) {
				bw1.write(tmp + "\r\n");
				//Log.d("写入SD", tmp);
			}
			bw1.flush();
			bw1.close();
			

			// 导出详细数据
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(file2, false)); // true// 是添加在后面// false// 是每次写新的
			bw2.write("详细数据 \r\n");
			list = myApplication.getInstance().ExprotTempData();
			for (String tmp : list) {
				bw2.write(tmp + "\r\n");
				Log.d("写入SD", tmp);
			}
			bw2.flush();
			bw2.close();
			
			//result = "导出完成，路径:./GREAN/";
			
			//Toast.makeText(historyActivity.this,"导出完成，路径:./GREAN/", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			//result = "导出失败，请检查外部存储器";
			e.printStackTrace();
			return false;
			//Toast.makeText(historyActivity.this,"导出失败，请检查外部存储器", Toast.LENGTH_SHORT).show();
			//Log.e("TestFile", "Error on writeFilToSD.");
		}
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		

		
		return true;
	}

}
