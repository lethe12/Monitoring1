package cn.com.grean.script.instruction;

public interface GhostCommand {
	/**
	 * 记录临时数据
	 * @param ghostRecord
	 * @param com
	 * @return
	 */
	public boolean execute (GhostRecord ghostRecord,CommandSerialPort com);
	
}
