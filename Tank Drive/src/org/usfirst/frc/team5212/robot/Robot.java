package org.usfirst.frc.team5212.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {

	
	/* talons for arcade drive */
	WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(0); 
	WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(2);

	/* extra talons and victors for six motor drives */
	
	//WPI_TalonSRX leftSlave = new WPI_TalonSRX(3);
	//WPI_TalonSRX rightSlave = new WPI_TalonSRX(1);
	
	//WPI_TalonSRX leftSecondSlave = new WPI_TalonSRX();
	//WPI_TalonSRX rightSecondSlave = new WPI_TalonSRX();
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(1);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(3);

	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(5);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(4);
	
	DifferentialDrive drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

	Joystick joy = new Joystick(0);
	
	
	
	NetworkTable table;
	NetworkTableInstance inst;

	NetworkTableEntry leftRawData;
	NetworkTableEntry rightRawData;
	
	final int DEFAULT_DRIVE = 0;
	//speed of robot [0-1]
	double speedR = .84;
	double speedL = 0.71;
	//double speedR = 1;
	//double speedL = 1;
	
	//UsbCamera camera0;
	//UsbCamera camera1;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		/*
		 * take our extra talons and just have them follow the Talons updated in
		 * arcadeDrive
		 */
		//leftSlave.follow(frontLeftMotor);
		//rightSlave.follow(frontRightMotor);
		
		
		leftSlave1.follow(frontLeftMotor);
		rightSlave1.follow(frontRightMotor);

		leftSlave2.follow(frontLeftMotor);
		rightSlave2.follow(frontRightMotor);
		
		/* drive robot forward and make sure all 
		 * motors spin the correct way.
		 * Toggle booleans accordingly.... */
		frontLeftMotor.setInverted(false);
		//leftSlave.setInverted(false);
		
		frontRightMotor.setInverted(false);
		//rightSlave.setInverted(false);
		
	}
	
	public void autonomousInit() {
		
	}
	
	public void autonomousPeriodic() {
		
		//camera1 = new UsbCamera("camera1", 1);
		//camera0 = new UsbCamera("camera0", 0);

		
		//camera1.setFPS(30);
		//camera1.setResolution(320, 240);
		
		//CameraServer.getInstance().startAutomaticCapture(camera1);
		
		//camera0.setFPS(30);
		//camera0.setResolution(320, 240);
		
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
//		/* sign this so forward is positive */
//		double forward = -1.0 * _joy0.getY();
//		/* sign this so right is positive. */
//		double turn = +1.0 * _joy0.getY();
//		/* deadband */
//		if (Math.abs(forward) < 0.10) {
//			/* within 10% joystick, make it zero */
//			forward = 0;
//		}
//		if (Math.abs(turn) < 0.10) {
//			/* within 10% joystick, make it zero */
//			turn = 0;
//		}
		/* print the joystick values to sign them, comment
		 * out this line after checking the joystick directions. */
//		System.out.println("JoyY:" + forward + "  turn:" + turn );
		/* drive the robot, when driving forward one side will be red.  
		 * This is because DifferentialDrive assumes 
		 * one side must be negative */
		
		System.out.println("Joy0Y:" + joy.getRawAxis(1) + "Joy1Y:" + joy.getRawAxis(3));
		
		drive.tankDrive(-(1) * (speedL)*joy.getRawAxis(1), -(1) * (speedR)*joy.getRawAxis(3));
		
		//float leftVal = leftRawData.getNumber(DEFAULT_DRIVE).floatValue();
		//float rightVal = rightRawData.getNumber(DEFAULT_DRIVE).floatValue();
	}
}