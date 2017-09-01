package cn.com.grean.script.instruction;
/**
 * 处理脚本结束流程
 * @author Administrator
 *
 */
public interface ScriptFinishCommand {
	void onComplete(CommandSerialPort com);
}
