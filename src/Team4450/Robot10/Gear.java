package Team4450.Robot10;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import Team4450.Lib.LCD;
import Team4450.Lib.Util;
import Team4450.Lib.ValveDA;
import Team4450.Lib.JoyStick.JoyStickButtonIDs;

public class Gear 
{
	private Robot robot;
	private Teleop teleop;
	
	private CANTalon motor = new CANTalon(7);
	
	private ValveDA PlacingValve = new ValveDA(1, 0);
	private ValveDA ElevatorValve = new ValveDA(6);

	private boolean pickupDown;
	private boolean pickupOut;
	
	public Gear(Robot robot, Teleop teleop)
	{
		Util.consoleLog();
		this.robot = robot;
		this.teleop = teleop;
		
		raiseGear();
		MotorStop();
		gearIn();
		
	}
	
	public void dispose()
	{
		Util.consoleLog();
		if (ElevatorValve != null) ElevatorValve.dispose();
		if (PlacingValve != null) PlacingValve.dispose();
	}
	
	public void MotorIn()
	{
		Util.consoleLog();
		motor.set(10); //Find out what power the motor should be
		SmartDashboard.putBoolean("PickUpGearMotor", true);
	}
	
	public void MotorOut()
	{
		Util.consoleLog();
		motor.set(-10); //Find out what power the motor should be
		SmartDashboard.putBoolean("PickupGearMotor", false);
	}
	
	public void MotorStop()
	{
		Util.consoleLog();
		motor.set(0);
		SmartDashboard.putBoolean("PickupGearMotor", false);
	}
	
	public void raiseGear()
	{
		Util.consoleLog();
		pickupDown = false;
		ElevatorValve.SetA();
		SmartDashboard.putBoolean("PickupGearDown", false);
		
	}
	
	
	public void lowerGear()
	{
		Util.consoleLog();
		pickupDown = true;
		ElevatorValve.SetB();
		SmartDashboard.putBoolean("PickupGearDown", true);
		
	}
	
	
	public void gearIn()
	{
		Util.consoleLog();
		pickupOut = false;
		PlacingValve.SetA();
		SmartDashboard.putBoolean("PickupGearDown", false);
	}
	
	
	public void gearOut()
	{
		Util.consoleLog();
		pickupOut = true;
		PlacingValve.SetB();
		SmartDashboard.putBoolean("PickupGearDown", true);
	}
	
	//If gear is planned for auton, then put that into code accordingly. 
	
	
}
