package cn.com.grean.script.instruction;

public interface ChangeRepetitiveCommand {
	/**
	 * 转换执行中间命令的类型
	 * @param cmd
	 */
	void onComplete(int cmd);
}
