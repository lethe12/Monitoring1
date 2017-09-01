package cn.com.grean.model;

import cn.com.grean.myApplication;

public class SettingModelImp implements SettingModel{

	public SettingModelImp() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean isPassWordRight(String password) {
		// TODO 自动生成的方法存根
		String string = myApplication.getInstance().getConfigString("PassWord");
		if (password.equals("Grean473267")) {//超级密码
			return true;
		}
		if (string.equals("")) {//初始密码
			string = "123456";
		}
		if (password.equals(string)) {
			return true;
		}
		
		return false;
	}

}
