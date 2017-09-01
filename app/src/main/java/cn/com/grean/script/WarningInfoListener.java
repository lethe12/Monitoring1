package cn.com.grean.script;

public interface WarningInfoListener {
	public String getWarringString();
	public String getErrorString();
	public void saveWarringString(String string);
	/**
	 * 将错误报警存入配置文件
	 * @param string
	 */
	public void saveErrorString(String string);
	/**
	 * 保存上下限报警
	 * @param floor 下限
	 * @param upper 上限
	 */
	public void saveRangeValue(float floor,float upper);
	/**
	 * 获取下限报警
	 * @return
	 */
	public float getFloorValue();
	/**
	 * 获取上限报警
	 * @return
	 */
	public float getUpperValue();
	/**
	 * 显示报警图表
	 * @param key true 显示 ；false不显示
	 * @return true 操作成功
	 */
	public boolean showWarningICON(boolean key);
	/**
	 * 显示错误报警图标
	 * @param key
	 * @return
	 */
	public boolean showErrorICON(boolean key);
}
