package cn.com.grean.script;

import cn.com.grean.script.algorithm.Compute;
/**
 * 数据库记录测量结果格式
 * @author Administrator
 *
 */
public class ResultDataFormat {
	private Compute compute;
	private long timestamp;
	public ResultDataFormat(Compute compute,long timestamp) {
		// TODO 自动生成的构造函数存根
		this.compute = compute;
		this.timestamp = timestamp;
	}
	
	public long getDate(){
		return timestamp;
	}
	
	public float getResult(){
		return compute.getComputeResult();
	}
	
	public String getText(){
		return compute.getTestInfo();
	}
	
	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return "ResultDataFormat";
	}

}
