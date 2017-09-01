package cn.com.grean.Presenter;

public interface DetailAdvancedPresenter {
	void showDetail();
	String [] getScriptNames();//获取脚本名称
	String [] getDeviceNames();//获取仪器名称
	String [] getCommunicationProtocolNames();//获取通讯协议名称
	String [] getVirtualDevicesNames();//获取虚拟设备名称
	boolean loadScript(int pos,int range);//导入脚本
	void interviewScript(int pos);//查看脚本
	void setChooseDevices(int pos);//选择对应的仪器
	void setChooseProtocol(int pos);//选择通讯协议
	int getNowDevices();//获取当前参数
	int getNowCommunictionProtocol();//获取当前通讯协议
	/**
	 * 获取虚拟设备参数
	 * @param pos
	 * @return
	 */
	String getVirtualDevices(int pos);
	/**
	 * 设置虚拟设备参数
	 * @param pos 选项
	 * @param params 内容
	 */
	void setVirtualDevices(int pos,String params);//设定虚拟设备参数
	void enableVirtualDevices();//使能当前指标的虚拟设备
	boolean saveCalParams(float [] params);//保存校准参数
	void savePassword(String password);//修改系统密码
	/**
	 * 保存升级URL
	 * @param uri
	 */
	void saveUpdataURI(String uri);
	/**
	 * 获取量程字符串
	 * @return
	 */
	String [] getDevicesRanges();
	/**
	 * 获取当前状态
	 * @return
	 */
	int getDevicesRange();
	/**
	 * 设置仪器量程
	 * @param range
	 */
	void setDevicesRange(int range);
}
