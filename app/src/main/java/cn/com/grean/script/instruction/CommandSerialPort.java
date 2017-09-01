package cn.com.grean.script.instruction;
/**
 * 定义脚本命令的接口
 * @author Administrator
 *
 */
public interface CommandSerialPort {
	public abstract void Send(byte [] data);
	public abstract Object SyncSend(byte [] data);

}
