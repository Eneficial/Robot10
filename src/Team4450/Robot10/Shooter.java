package Team4450.Robot10;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import Team4450.Lib.Util;
import edu.wpi.first.wpilibj.Spark;

public class Shooter {

	private final Robot 	robot;
	
	public static Shooter getInstance(Robot robot) {
		if (shooter == null) shooter = new Shooter(robot);
		return shooter;
	}
	private static Shooter shooter;

	public Spark		motor = new Spark(0);
	public Talon		feederMotor = new Talon(1);
	private Talon		intakeMotor = new Talon(3);
	private Talon		indexerMotor = new Talon(2);

	public Counter tlEncoder = new Counter(0);
	
	static final double Intake_Power = 0.30; //clone is -0.25
	static final double Index_Power = -0.50; //clone is -0.80
	static final double Motor_Power = 0.80;
	
	static final double SHOOTER_LOW_POWER = 0.50;
	static final double SHOOTER_HIGH_POWER = 0.45;
	static final double SHOOTER_LOW_RPM = 3100;
	static final double SHOOTER_HIGH_RPM = 3100; //3000 for clone bot
	
	public double ValueP = 0.0025;
	public double ValueI = 0.0025;
	public double ValueD = 0.005; //0.003 for clone bot
	

	private PIDController PIDController;
	public ShooterSpeed	shooterSpeed = new ShooterSpeed(tlEncoder);

	
	private Shooter(Robot robot) {
		
		Util.consoleLog();
		this.robot = robot;
		tlEncoder.reset();
		tlEncoder.setDistancePerPulse(1);
		tlEncoder.setPIDSourceType(PIDSourceType.kRate);
		PIDController = new PIDController(0.0,0.0,0.0, shooterSpeed, motor);
		stopInit();
		stopIntake();
		ballIntakeStop();
	}
	
	public void dispose() {
		Util.consoleLog();
		
		if (motor != null) motor.free();
		if (intakeMotor != null) intakeMotor.free();
		if (indexerMotor != null) indexerMotor.free();
		if (tlEncoder != null) tlEncoder.free();
		if (PIDController != null)
		{
			//PIDController.disable();
			PIDController.free();
		}
	}
	
	public void Initalizing(double power) {
		Util.consoleLog();
		SmartDashboard.putBoolean("PIDEnabled", false);
		if (power == SHOOTER_LOW_POWER) {
			holdShooterRPM(SmartDashboard.getNumber("LowSetting", SHOOTER_LOW_RPM));
	}
		if (power == SHOOTER_HIGH_POWER) {
			holdShooterRPM(SmartDashboard.getNumber("HighSetting", SHOOTER_HIGH_RPM));
		}
		
		SmartDashboard.putBoolean("ShooterMotor", true);
	}

	
	public void stopInit() {
		Util.consoleLog();
		SmartDashboard.getBoolean("ShooterMotor", false);
		PIDController.disable();
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
	
	public void IntakeReverse() {
		Util.consoleLog();
		SmartDashboard.putBoolean("DispenserMotor", true);
		intakeMotor.set(-Intake_Power);
	}
		
		public void ballIntake() {
			Util.consoleLog();
			SmartDashboard.putBoolean("BallPickupMotor", true);
			motor.set(Motor_Power);
	}
		
		public void ballIntakeStop() {
			Util.consoleLog();
			SmartDashboard.putBoolean("BallPickupMotor", false);
			motor.set(0);
	}
		
		public boolean isRunning()
		{
			if (motor.get() != 0)
				return true;
			else
				return false;
		}
		
		public void holdShooterRPM(double rpm) {
			Util.consoleLog();
			double ValueP = SmartDashboard.getNumber("ValueP", this.ValueP);
			double ValueI = SmartDashboard.getNumber("ValueI", this.ValueI);
			double ValueD = SmartDashboard.getNumber("ValueD", this.ValueD);
			
			PIDController.setPID(ValueP, ValueI, ValueD, 0.0);
			PIDController.setSetpoint(rpm / 60);		
			PIDController.setPercentTolerance(5);	
			PIDController.setToleranceBuffer(4096);
			PIDController.setContinuous();
			PIDController.reset();
			PIDController.enable();
		}
		
		public class ShooterSpeed implements PIDSource {
			private Encoder		encoder;
			private Counter		counter;
			private int			inversion = 1;
			private double		rpmAccumulator, rpmSampleCount;
			
			public ShooterSpeed(Encoder encoder)
			{
				this.encoder = encoder;
			}
			
			public ShooterSpeed(Counter counter)
			{
				this.counter = counter;
			}
			
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
				if (encoder != null) encoder.setPIDSourceType(pidSource);
				if (counter != null) counter.setPIDSourceType(pidSource);
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				if (encoder != null) return encoder.getPIDSourceType();
				if (counter != null) return counter.getPIDSourceType();

				return null;
			}
			
			public void setInverted(boolean inverted) {
				if (inverted)
					inversion = -1;
				else
					inversion = 1;
			}

			public int get() {
				if (encoder != null ) return encoder.get() * inversion;
				if (counter != null ) return counter.get() * inversion;
				
				return 0;
			}
			
			public double getRate() {

				if (encoder != null) return encoder.getRate() * inversion;
				if (counter != null) return counter.getRate() * inversion;
				
				return 0;
			}
			
			@Override
			public double pidGet() {
				if (encoder != null)
				{
	    			if (encoder.getPIDSourceType() == PIDSourceType.kRate)
	    				return getRate();
	    			else
	    				return get();
				}
				
				if (counter != null)
				{
	    			if (counter.getPIDSourceType() == PIDSourceType.kRate)
	    				return getRate();
	    			else
	    				return get();
				}
				
				return 0;
			}
			
			public void reset() {
				rpmAccumulator = rpmSampleCount = 0;
				
				if (encoder != null) encoder.reset();
				if (counter != null) counter.reset();
			}
		}
}

		


