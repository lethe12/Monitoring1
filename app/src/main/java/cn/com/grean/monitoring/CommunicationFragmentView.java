package cn.com.grean.monitoring;

import cn.com.grean.model.CommunicationInfo;

public interface CommunicationFragmentView {
	void showCommunicationInfo(CommunicationInfo info);//显示通讯细节
	void updataTestCmd(String text);//更新预计测试的命令
	void updataCommunicationMode(boolean key);//更新主机或从机模式
	/**
	 * 显示联网图标
	 * @param key
	 */
	void showTCPConnect(boolean key);
}
