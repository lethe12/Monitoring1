package cn.com.grean.script.instruction;

import cn.com.grean.script.LogListener;

public interface Command {
	/**
	 * 不改变当前中间命令
	 */
	public final static int NoChangeGhost = 0;
	/**
	 * 开启记录中间命令
	 */
	public final static int EnableChangeGhost = 1;
	/**
	 * 关闭中间命令
	 */
	public final static int DisableChangeGhost = 2;
	/**
	 * 
	 * @param params 该语句命令的参数，参考《脚本说明》
	 * @param com 脚本对应的COM口
	 * @param ghostRecord 中间语句记录数据的接口
	 * @param ghostCommand 中间语句执行接口
	 * @param logListener 日志监听
	 * @param info 脚本中的文本信息
	 * @return 需要执行的中间步骤
	 */
	public abstract int execute(int [] params,CommandSerialPort com,GhostRecord ghostRecord,GhostCommand ghostCommand,LogListener logListener,String info);
	/**
	 * 更改中间命令的方法
	 * @return NoChangeGhost 无变化;
	 * EnableChangeGhost 开启侦听数据;
	 * DisableChangeGhost 关闭侦听数据;
	 * EnableTNGhost 开启总氮的侦听模式;
	 * DisableTNGhost 关闭总氮侦听模式;
	 * EnableLevelGhost 开启液位侦听;
	 * DisableLevelGhost 关闭液位侦听;
	 * EnableTitrationGhost 开启滴定侦听;
	 * DisableTitrationGhost 关闭滴定侦听;
	 */
	//public int changeGhostCommand();

	/**
	 * 执行中间命令
	 * @return
	 */
	public boolean RepetitiveExecute ();
	/** 
	 * 结束重复命令需执行的方法
	 * @param change 转换中间命令的接口
	 */
	public void end(ChangeRepetitiveCommand change);
}
