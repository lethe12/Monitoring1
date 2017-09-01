package cn.com.grean.script.instruction;

public class RecordGhostCommand implements GhostCommand{

	public RecordGhostCommand() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean execute(GhostRecord ghostRecord, CommandSerialPort com) {
		// TODO 自动生成的方法存根
		byte [] cmd = {0x01,0x07,0x01,0x00,0x0D,0x0A};
		GeneralData data = (GeneralData) com.SyncSend(cmd);
		if (ghostRecord!=null) {
			ghostRecord.Record(data);
		}
		return false;
	}

	

	

}
