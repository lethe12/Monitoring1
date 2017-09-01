package cn.com.grean.script;

import cn.com.grean.script.algorithm.Compute;

/**
 * 监听测量和校准结果
 * @author Administrator
 *
 */
public interface ResultListener {
	void onResultComplete(Compute compute);//测量结束流程
	void onCalibrationComplete(String info);//校准结束流程
	void onErrorComplete(String info);//异常结束流程
}
