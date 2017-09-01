package cn.com.grean.script.plan;

public class ScriptChain implements ChainOfScript{
	private String name;
	public ScriptChain(String name) {
		// TODO 自动生成的构造函数存根
		this.name = name;
	}

	@Override
	public String getScriptName() {
		// TODO 自动生成的方法存根
		return name;
	}

}
