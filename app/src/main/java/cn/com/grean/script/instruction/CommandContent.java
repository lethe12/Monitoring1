package cn.com.grean.script.instruction;


public class CommandContent {

	public CommandContent() {
		// TODO 自动生成的构造函数存根
	}
	
	public static GhostCommand getGhostCommand(String type) {
		if (type.equals("General")) {
			return new RecordGhostCommand();
		}
		else {
			return new NonGhostCommand();
		}
	}
	
	public static Command getCommand(String type){
		if(type.equals("Pump")){
			return new PumpCommand();
		}else if (type.equals("Valve")) {
			return new ValveCommand();
		}else if (type.equals("Sleep")) {
			return new SleepCommand();
		}else if (type.equals("CH")) {
			return new CHxCommand();
		}else if (type.equals("Note")) {
			return new NoteCommand();
		}else if(type.equals("BackTitration")){
			return new TitrationCommand();
		}else if (type.equals("Read")||type.equals("READ")) {
			return new ReadCommand();
		}else if (type.equals("VD")) {
			return new VirtualDeviceCommand();
		}else if (type.equals("Level")) {
			return new LevelCommand();
		}else if (type.equals("AllCH")) {
			return new AllchCommand();
		}else if (type.equals("Mean")) {
			return new MeanConmmand();
		}else if (type.equals("Average")) {
			return new AverageCommand();
		}else if (type.equals("TNAverage")) {
			return new TNAverageCommand();
		}else if (type.equals("Lap")) {
			return new LapCommand();
		}else if (type.equals("PumpZQ")) {
			return new PumpZQCommand();
		}else if (type.equals("CheckWarring")) {
			return new CheckWarringCommand();
		}else if(type.equals("Pose")){
			return new PoseCommand();
		}else if(type.equals("PoseZero")){
            return new PoseZeroCommand();
        }else if(type.equals("Inject")){
			return new Inject();
		}else if(type.equals("InjectZero")){
			return new InjectZero();
		}else{
			return new NonCommand();
		}
		
		
	}

}
