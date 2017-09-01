package cn.com.grean.script.instruction;

import java.util.Hashtable;

/**
 * 指令的Flyweight工厂
 * @author Administrator
 *
 */
public class CommandFactory {
	@SuppressWarnings("unused")
	private final static String tag = "CommandFactory";
	private Hashtable<String, Command> pool = new Hashtable<String, Command>();
	private Hashtable<String, GhostCommand> ghostPool = new Hashtable<String, GhostCommand>();
	private static CommandFactory singleton = new CommandFactory();
	private String cmdType;
	private int [] params;
	private String content;
	public String getCmdType() {
		return cmdType;
	}

	public int[] getParams() {
		return params;
	}

	public String getContent() {
		return content;
	}

	private CommandFactory() {
		// TODO 自动生成的构造函数存根
	}
	
	public void translateCommand(String cmd){
		String text = cmd.replace(" ", "");
		String [] temp = text.split(",");
		int index=0;
		if (temp.length <2) {
			cmdType = "NON";
		}
		else {
			if (temp[index].equals("TP")) {
				index++;
			}
			cmdType = temp[index++];
			if (cmdType.equals("Note")) {
				content = temp[index];
			}
			else if(cmdType.equals("Starttest")){
				content = temp[index];
			}
			else if (cmdType.equals("Endtest")) {//清空显示区
				content = " ";
			}
			else if (cmdType.equals("CheckWarring")||cmdType.equals("CheckError")) {
				content = temp[index++];
				int len = temp.length - index;
				for (int i = 0; i < len; i++) {
					params[i] = Integer.valueOf(temp[index++]);					
				}
			}
			else{
				int len = temp.length - index;
				params = new int[len];
				for (int i = 0; i < len; i++) {
					params[i] = Integer.valueOf(temp[index++]);					
				}
				content = null;
			}
		}
	}
	
	public static CommandFactory getInstance(){
		return singleton;
	}
	public synchronized GhostCommand getGhostCommand(String name){
		GhostCommand gc = ghostPool.get(name);
		if (gc == null) {
			gc = CommandContent.getGhostCommand(name);
			ghostPool.put(name, gc);
		}
		return gc;
	}
	
	public synchronized Command getCommandContent (){
		Command cc = pool.get(cmdType);
		if (cc==null) {
			cc = CommandContent.getCommand(cmdType);
			pool.put(cmdType, cc);
		}
		return cc;
	}
	
	

}
