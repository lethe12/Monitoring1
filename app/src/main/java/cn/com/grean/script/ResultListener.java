package cn.com.grean.script;

import cn.com.grean.script.algorithm.Compute;
import cn.com.grean.script.algorithm.MultiSampleComputer;

/**
 * 监听测量和校准结果
 * @author Administrator
 *
 */
public interface ResultListener {
	void onResultComplete(Compute compute);//测量结束流程
	void onResultsComplete(MultiSampleComputer computer,int sampleNumber);
	void onCalibrationComplete(String info);//校准结束流程
	void onErrorComplete(String info);//异常结束流程
}
