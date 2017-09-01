package cn.com.grean.monitoring;

public interface AdvancedFragmentView {
	/**
	 * 显示高级界面下
	 * @param params  斜率截距上下限
	 * @param devicesName 当前仪器名称
	 * @param communicationProtocol 通讯协议
	 * @param devicesRange 仪器量程
	 * @param uri 升级URI
	 */
	void showDetail(float [] params,int devicesName,int communicationProtocol,int devicesRange,String uri);//显示静态信息
	void showScriptContent(String [] list);
}
