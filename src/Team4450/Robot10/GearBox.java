package Team4450.Robot10;

import Team4450.Lib.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearBox {

	private Robot robot;
	
	public boolean gearLow = false; 
	public boolean gearHigh = false;
	public boolean neutral = false;
	public boolean PTO = false;
	
	public ValveDA shift = new ValveDA(0); //TODO: Get right #
	public ValveDA neutralValve = new ValveDA(0); //TODO: Get right #
	public ValveDA valve = new ValveDA(0); //TODO: Get right #  
	
	
	public GearBox (Robot robot)
	{
		Util.consoleLog();
		this.robot = robot;
		PTOoff();
		gearLow();
	}
	
	public void dispose()
	{
		Util.consoleLog();
		if (shift != null) shift.dispose();
		if (valve != null) valve.dispose();
		if (neutralValve != null) neutralValve.dispose();
	}
	
	public void dashDisplay() {
		Util.consoleLog();
		SmartDashboard.putBoolean("gearLow", gearLow);
		SmartDashboard.putBoolean("Neutral", neutral);
		SmartDashboard.putBoolean("PTO", PTO);
	}
	
	
	public void gearLow() {
		if (!gearLow) {
			shift.SetA();
		}
		else if (!neutral) {
			neutralValve.SetA();
			shift.SetA();
		}
		neutral = false;
		gearLow = true;
		dashDisplay();
	}
	
	
	public void gearHigh() {
		if (gearLow) {
			shift.SetB();
		}
		else if (neutral) { 
			neutralValve.SetA();
			shift.SetA();
		}
		neutral = false;
		gearLow = false;
		dashDisplay();
	}
	
	
	public void neutral() {
		if (!gearLow) {
			neutralValve.SetB();
		}
		else if (gearLow) {
			shift.SetA();
			neutralValve.SetB();
		}
		gearLow = true;
		neutral = true;
		dashDisplay();
	}
	
	public void PTOon()
	{
		Util.consoleLog();
		neutral();
		valve.SetA();
		PTO = true;
		dashDisplay();
	}
	
	public void PTOoff()
	{
		Util.consoleLog();
		PTO = false;
		valve.SetB();
		gearLow();
		dashDisplay();
	}

}
