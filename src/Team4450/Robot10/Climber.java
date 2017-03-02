package Team4450.Robot10;

import com.ctre.CANTalon;

import Team4450.Lib.Util;


public class Climber {
	private final Robot robot;
	private final GearBox gearBox;
	
	//TODO: Find out how many motors we'll be using for climb
	private final CANTalon climbMotor = new CANTalon(0); //Get right number
	//private final CANTalon climbMotor2 = new CANTalon(0); //Get right number
	
	
	public Climber(Robot robot, GearBox gearBox)
	{
		Util.consoleLog();
		this.robot = robot;
		this.gearBox = gearBox;
	}
	
	public void dispose()
	{
		Util.consoleLog();
		if (climbMotor != null) climbMotor.delete();
	}
	
	public void climbStart()
	{
		Util.consoleLog();
		gearBox.PTOon();
		robot.LFCanTalon.set(0);
		robot.RFCanTalon.set(0);
		robot.LRCanTalon.set(0); 
		robot.RRCanTalon.set(0); 
		climbMotor.set(50); //Find actual power that I want to set this to.
	}
	
	public void climbStop() 
	{
		Util.consoleLog();
		gearBox.PTOoff();
	}
	

}
