package Team4450.Robot10;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import Team4450.Lib.Util;

public class Shooter {

	private final Robot 	robot;

	private Talon		motor = new Talon(1);
	private Talon		intakeMotor = new Talon(2);
	private Talon		indexerMotor = new Talon(3);

	public Encoder encoder = new Encoder(3, 4, true, EncodingType.k4X);
	
	static final double Intake_Power = 0.30;
	static final double Index_Power = -0.50;
	static final double Motor_Power = 0.80;

	
	public Shooter(Robot robot) {
		
		Util.consoleLog();
		this.robot = robot;
		encoder.reset();
		stopShoot();

	}
	
	public void dispose() {
		Util.consoleLog();
		
		if (motor != null) motor.free();
		if (encoder != null) encoder.free();
		if (intakeMotor != null) intakeMotor.free();
		if (indexerMotor != null) indexerMotor.free();
	}
	
	public void Shooting() {
		Util.consoleLog();
		SmartDashboard.putBoolean("ShooterMotor", true);
		motor.set(Motor_Power);
	}
	
	public void stopShoot() {
		Util.consoleLog();
		SmartDashboard.putBoolean("ShooterMotor", true);
		motor.set(0);
	}
	
	public void Intake() {
		Util.consoleLog();
		SmartDashboard.putBoolean("DispenserMotor", true);
		intakeMotor.set(Intake_Power);
		indexerMotor.set(Index_Power);
	}

	public void stopIntake() {
		Util.consoleLog();
		SmartDashboard.putBoolean("DispenserMotor", false);
		intakeMotor.set(0);
		indexerMotor.set(0);
	}
}
