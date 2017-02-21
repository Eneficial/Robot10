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
	
	private Thread GearThread;
	
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
	
	public void StartAutoPickup()
	{
		Util.consoleLog();
		
		if (GearThread != null) return;

		GearThread = new AutoPickup();
		GearThread.start();
	}
	

	public void StopAutoPickup()
	{
		Util.consoleLog();

		if (GearThread != null) GearThread.interrupt();
		
		GearThread = null;
	}

	
	private class AutoPickup extends Thread
	{
		AutoPickup()
		{
			Util.consoleLog();
			
			this.setName("AutoGearPickup");
	    }
		
	    public void run()
	    {
	    	Util.consoleLog();
	    	
	    	try
	    	{
	    		lowerGear();
	    		sleep(250);
	    		gearIn();
	    		
    	    	while (!isInterrupted() && motor.getOutputCurrent() < 1.0)
    	    	{
    	            // We sleep since JS updates come from DS every 20ms or so. We wait 50ms so this thread
    	            // does not run at the same time as the teleop thread.
    	    		LCD.printLine(7, "gearmotor current=%f", motor.getOutputCurrent());
    	            sleep(50);
    	    	}
	    	}
	    	catch (InterruptedException e) {}
	    	catch (Throwable e) {e.printStackTrace(Util.logPrintStream);}

	    	MotorStop();
			raiseGear();
			
			GearThread = null;
	    }
	}	
}
	
	
