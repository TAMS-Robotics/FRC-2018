package org.usfirst.frc.team5212.robot;

import org.usfirst.frc.team5212.autonomous.subsystems.DriveTrain;
import org.usfirst.frc.team5212.autonomous.subsystems.PIDDrive;
import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;

//import org.usfirst.frc.team5212.autonomous.subsystems.PIDDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;


public class Robot extends IterativeRobot {
	//states
	boolean compress_state = false;
	boolean shoot_state = true;
	
	public static PowerDistributionPanel panel;
	
	// subsystem creations
	
	public static PIDDrive piddrive;
	public static DriveTrain drivetrain;
	public static Pneumatics pneum;

	
//	NetworkTable table;
//	NetworkTableInstance inst;
//
//	NetworkTableEntry leftRawData;
//	NetworkTableEntry rightRawData;
	
	final int DEFAULT_DRIVE = 0;	
	
	// logs the input of the left joystick at the top of every teleopPeriodic
	double leftInput;
	// logs the input of the right joystick
	double rightInput;
	
//	public static final PIDDrive PIDDrive = new PIDDrive();
	public static OI oi;
	

	public void robotInit() {
		/*
		 * take our extra talons and just have them follow the Talons updated in
		 * arcadeDrive
		 */
		// ac = new PrepareShoot();
		
		panel = new PowerDistributionPanel(RobotMap.pdpPort);

		piddrive = new PIDDrive();
		drivetrain = new DriveTrain();
		pneum = new Pneumatics();
		oi = new OI();
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void autonomousInit() {
//		if (ac != null) {
//			// ac.start();
//		}
	}
	
	public void autonomousPeriodic() {
	}
	
	public void teleopInit() {
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
//		drivetrain.slewTankDrive(OI.j.getRawAxis(1), OI.j.getRawAxis(3), panel.getVoltage());
		
		leftInput = oi.getLeftJoystick();
		rightInput = oi.getRightJoystick();
		
//		Scheduler.getInstance().run();
				
		drivetrain.slewTankDrive(leftInput, rightInput, panel.getVoltage());
		
//		drive.tankDrive(leftInput, rightInput);
	}
	
}