package cn.com.grean.model;

import java.io.IOException;

import cn.com.grean.SystemDateTime;
import cn.com.grean.myApplication;
import cn.com.grean.tools;

public class DetailSystem implements DetailSystemModel{
	private String [] backLightStrings = new String []{"5","15","30"};
	private int backLightTime;//背光时间 单位 min
	private int pos;//位置
	public DetailSystem() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public int getBackLightTime() {
		// TODO 自动生成的方法存根
		return pos;
	}

	@Override
	public void loadBackLightTime() {
		// TODO 自动生成的方法存根
		backLightTime = myApplication.getInstance().getConfigInt("BackLightTime");
		switch (backLightTime) {
		case 5:
			pos = 0;
			break;
		case 15:
			pos = 1;
			break;
		case 30:
			pos = 2;
			break;
		default:
			pos =2;
			break;
		}
	}

	@Override
	public void setSystem(int year, int month, int day, int hour, int min) {
		// TODO 自动生成的方法存根
		try {
			SystemDateTime.setDateTime(year, month, day, hour, min);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Override
	public String[] loadBackLightTimeString() {
		// TODO 自动生成的方法存根
		return backLightStrings;
	}

	@Override
	public void setBackLightTime(int pos) {
		// TODO 自动生成的方法存根
		switch (pos) {
		case 0:
			backLightTime = 5;
			break;
		case 1:
			backLightTime = 15;
			break;
		case 2:
			backLightTime = 30;
			break;

		default:
			backLightTime = 30;
			break;
		}
		myApplication.getInstance().putConfig("BackLightTime", backLightTime);
	}

	@Override
	public String getNowDateString() {
		// TODO 自动生成的方法存根
		
		return tools.nowDate2string();
	}

}
