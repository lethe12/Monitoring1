package cn.com.grean.script.algorithm;
/**
 * 双波长检测使用的计算接口
 * @author Administrator
 *
 */
public interface DualWaveCompute {

	/**
	 * 设置第一次测量值
	 * @param data 测量通道
	 * @param ref 参比通道
	 */
	public void setFirstValue(float data,float ref);
	/**
	 * 计算结果
	 * @param data 测量通道
	 * @param ref 参比通道
	 * @return
	 */
	public float Result(float data , float ref);
	/**
	 * 高点校准
	 * @param data 参量通道
	 * @param ref 参比通道
	 * @param index 当前校准指针
	 * @param times 总得校准平均次数
	 */
	public void calibrationHigh(float data, float ref,int index,int times);
	/**
	 * 低点校准方法
	 * @param data 当前信号
	 * @param ref 参比通道
	 * @param index 当前校准指针
	 * @param times 总得校准平均次数
	 */
	public void calibrationLow(float data , float ref,int index,int times);
	/**
	 *获取浊度补偿系数 
	 * @return
	 */
	public float getTurbCompenation();
	/**
	 * 设置浊度补偿系数
	 * @param data
	 */
	public void setTurbCompenation(float data);
}
