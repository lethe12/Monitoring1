package cn.com.grean.script;

import java.util.LinkedList;

public class WarningManager {
	private String overRangeString;
	private LinkedList<String>warringStrings = new LinkedList<String>();
	private String noSampleString;
	private float floor,upper;
	private boolean isWarning = false;//当前是否已经报警标志位
	private boolean isError = false;//当前已经错误报警
	private static WarningManager instance = new WarningManager();
	private WarningManager() {
		// TODO 自动生成的构造函数存根
	}
	public static WarningManager getInstance() {
		return instance;
	}
	public void clearWarringInfo(WarningInfoListener listener){
		overRangeString="";
		//warringStrings.clear();
		listener.saveWarringString("");
		isWarning = false;
		listener.showWarningICON(false);
	}
	public void clearErrorInfo(WarningInfoListener listener ){
		noSampleString="";
		isError = false;
		listener.saveErrorString("");
		listener.showErrorICON(false);
	}
	
	public String getWarringString(){
		String temp = overRangeString;
		if (warringStrings!=null) {			
			int len = warringStrings.size();
			for (int i = 0; i < len; i++) {
				temp = temp+warringStrings.get(i)+"\n";
				
			}
		}
		return temp;
	}
	
	public String getErrorString(){
		
		return noSampleString;
	}
	/**
	 * 增加警告信息
	 * @param string
	 */
	public void addWarringString(String string){
		warringStrings.add(string);
	}
	
	public void setRangeValue(float floor,float upper,WarningInfoListener listener){
		this.floor = floor;
		this.upper = upper;
		listener.saveRangeValue(floor, upper);
		overRangeString = listener.getWarringString();
		noSampleString = listener.getErrorString();
	}
	/**
	 * 载入当前存储的上下限
	 * @param listener
	 */
	public void loadRangevalue(WarningInfoListener listener){
		floor = listener.getFloorValue();
		upper = listener.getUpperValue();
		
	}
	public float getFloor() {
		return floor;
	}
	
	public float getUpper() {
		return upper;
	}
	
	public void warningOverLimit(float result,WarningInfoListener listener){
		if (result > upper) {
			overRangeString = "超出报警上限\n";
			isWarning = true;
		}else if (result < floor) {
			overRangeString = "低于报警下限\n";
			isWarning = true;
		}else {
			if (warringStrings!=null) {				
				if (warringStrings.isEmpty()) {//没有其他报警,则清除报警
					isWarning = false;
				}
			}else {
				isWarning = false;
			}
			
		}
		
		listener.showWarningICON(isWarning);
				
	}
}
