package cn.com.grean.script;

public interface ScriptRunListener {
	void updataCommandInfo(String info,int Progress,int max);
	void updataResultInfo(String result,String testTime);
	
}
