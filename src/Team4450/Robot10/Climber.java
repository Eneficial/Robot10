package Team4450.Robot10;

//import com.ctre.CANTalon;

import Team4450.Lib.Util;


public class Climber {
	private final Robot robot;
	private final GearBox gearBox;
	
	private boolean climbPrepared = false;
	
	//TODO: Find out how many motors we'll be using for climb
	//private final CANTalon climbMotor = new CANTalon(0); //Get right number
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
		//if (climbMotor != null) climbMotor.dispose();
	}
	
	public void climbStart()
	{
		Util.consoleLog();
		gearBox.PTOon();
		climbPrepared = true;
	}
	
	public void climbStop() 
	{
		Util.consoleLog();
		climbPrepared = false;
		gearBox.PTOoff();
	}
	
	public void Climb(double power)
	{
		Util.consoleLog();
		if (climbPrepared == true)
		{
			robot.LFCanTalon.set(0); //Get real power
			robot.RFCanTalon.set(0); //Get real power
			robot.LRCanTalon.set(0); //Get real power
			robot.RRCanTalon.set(0); //Get real power
		}
	}
	

}
