package cn.com.grean.script.instruction;

import android.util.Log;

import cn.com.grean.tools;



/**
 * 
 * @author Administrator
 *
 */
public class GeneralData {
	private static final String tag ="GeneralData";
	private float [] data = new float[9];
	public float[] getData() {
		return data;
	}
	public void setData(float[] data) {
		if (data.length == 9) {
			System.arraycopy(data, 0, this.data, 0, 8);
		}
		this.data = data;
	}
	public GeneralData() {
		// TODO 自动生成的构造函数存根
	}
	public void updata(int index,float data){
		if ((index < 9)&&(index>=0)) {
			this.data[index] = data;
		}
	}
	public float getOne(int index){
		if ((index < 9)&&(index>=0)) {
			return this.data[index];
		}
		else{
			return 0f;
		}
	}
	/**
	 * 直接将字符转成一般
	 * @param data
	 */
	public void setData(byte [] data,int len){
		if ((data.length >= 41)&&(len == 41)) {
			for (int i = 0; i < 9; i++) {
				this.data[i] = tools.getFloat(data, 4*i+3);
				//Log.d(tag,String.valueOf(this.data[i]));
			}

		}
	}

}
