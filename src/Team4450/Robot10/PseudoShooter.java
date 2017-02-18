/*package Team4450.Robot10;

import Team4450.Lib.*;

import edu.wpi.first.wpilibj.Talon;


import com.ctre.*;
import com.ctre.CANTalon.*;

public class PseudoShooter {

	private final Robot 	robot;
	private final Teleop	teleop;

	private final Talon		shooterMotor1;
	private final Talon		shooterMotor2;
	private final Talon		intakeMotor;

	public double Intake_Power, Shooter_Power;

	private boolean intake;
	private boolean shoot;


	PseudoShooter(Robot robot, Teleop teleop) {
		Util.consoleLog();
		
		this.robot = robot;
		this.teleop = teleop;

		Intake_Power = 0.0; //Get the correct intake power value
		Shooter_Power = 0.0; //Get the correct shooter power value
		
		robot.InitializeCANTalon(intakeMotor = new CANTalon(0)); //Get the correct number
		robot.InitializeCANTalon(shooterMotor1 = new CANTalon(1)); //Get the correct number
		robot.InitializeCANTalon(shooterMotor2 = new CANTalon(2)); //Get the correct number


	}

	public void stopintake() {
		if (intake) {
			intakeMotor.set(0); //Get the intake number
			intake = false;
		} else {
			Util.consoleLog("Intake failed! Intook while intaking!");

		}
	}

	public void intake() {
		if (!intake) {
			intake = true;
			intakeMotor.set(Intake_Power);
		} else {
			Util.consoleLog("Intake failed! Intook while intaking!");
		}
	}

	public void shoot() {
		if (!shoot) {
			shoot = true;
			shooterMotor1.set(Shooter_Power); 
		} else {
			Util.consoleLog("Shoot failed! Shot while shooting!");
		}
	}

	public void stopshoot() {
		if (shoot) {
			shooterMotor1.set(0); //Get the intake number
			shoot = false;
		} else {
			Util.consoleLog("Shoot failed! Shot while shooting!");

		}
	}
}
*/

