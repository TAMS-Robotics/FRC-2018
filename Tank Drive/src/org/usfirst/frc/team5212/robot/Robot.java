package org.usfirst.frc.team5212.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;

import org.usfirst.frc.team5212.autonomous.commands.*;
import org.usfirst.frc.team5212.autonomous.subsystems.DriveBase;
import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.ArrayDeque;
import java.util.Queue;
import Math


public class Robot extends IterativeRobot {
	//states
	boolean compress_state = false;
	boolean shoot_state = true;
	
	// subsystem creations
//	public static DriveBase driveBase;
//	public static Pneumatics pneum;

	/* talons for arcade drive */
	WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftPort); //0 1 5
	WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightPort); //2 3 4

	/* extra talons and victors for six motor drives */

	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftSlave1Port);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightSlave1Port);

	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(RobotMap.leftSlave2Port);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(RobotMap.rightSlave2Port);
	
	DifferentialDrive drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

	// Command ac;
	
//	NetworkTable table;
//	NetworkTableInstance inst;
//
//	NetworkTableEntry leftRawData;
//	NetworkTableEntry rightRawData;
	PowerDistributionPanel panel;
	
	final int DEFAULT_DRIVE = 0;
	// speed of robot [0-1]
	double speedR = 0.5;
	double speedL = 0.5;
	
	double leftInput;
	double rightInput;
	
	Timer timer;
	double lastTenthSecond;
	Queue<Double> leftInputHistory;
	Queue<Double> rightInputHistory;
	double jumpThreshold;
	boolean leftInputJump;
	boolean rightInputJump;
	double leftPrevious;
	double rightPrevious;
	
	
	// OI oi;
	
	// UsbCamera camera0;
	// UsbCamera camera1;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		/*
		 * take our extra talons and just have them follow the Talons updated in
		 * arcadeDrive
		 */
		// // oi = new OI();
		// ac = new PrepareShoot();
		
//		driveBase = new DriveBase();
		// pneum = new Pneumatics();
		
		timer = new Timer();
		
		leftInputHistory = new ArrayDeque<Double>();
		rightInputHistory = new ArrayDeque<Double>();
		for(double i=0; i < 10; i++) {
			leftInputHistory.add(0.);
			rightInputHistory.add(0.);
		}
		
		jumpThreshold = 255/3;
		
		leftInputJump = false;
		rightInputJump = false;
		
		leftSlave1.follow(frontLeftMotor);
		rightSlave1.follow(frontRightMotor);

		leftSlave2.follow(frontLeftMotor);
		rightSlave2.follow(frontRightMotor);
		
		panel = new PowerDistributionPanel(RobotMap.pdpPort);
		
		
		
		/* drive robot forward and make sure all 
		 * motors spin the correct way.
		 * Toggle booleans accordingly.... */
		frontLeftMotor.setInverted(false);
		// leftSlave.setInverted(false);
		
		frontRightMotor.setInverted(false);
		// rightSlave.setInverted(false);
		
		// Pneumatics Setup
		//mainCompressor.setClosedLoopControl(true);
		//mainCompressor.stop();
//		solenoidOne.set(DoubleSolenoid.Value.kOff);

	}
	
	public void autonomousInit() {
//		if (ac != null) {
//			// ac.start();
//		}
	}
	
	public void autonomousPeriodic() {
		
		//camera1 = new UsbCamera("camera1", 1);
		//camera0 = new UsbCamera("camera0", 0);

		
		//camera1.setFPS(30);
		//camera1.setResolution(320, 240);
		
		//CameraServer.getInstance().startAutomaticCapture(camera1);
		
		//camera0.setFPS(30);
		//camera0.setResolution(320, 240);
		
		// Scheduler.getInstance().run();
		CameraServer.getInstance().startAutomaticCapture();
		// mainCompressor.setClosedLoopControl(false);
	}
	
	public void teleopInit() {
		lastTenthSecond = timer.get();
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
		
		leftInput = OI.j.getRawAxis(1);
		rightInput = OI.j.getRawAxis(3);
		
		if(timer.get() - lastTenthSecond >= 0.1) {
			leftInputHistory.remove();
			leftInputHistory.add(leftInput));
			rightInputHistory.remove();
			rightInputHistory.add(rightInput);
			lastTenthSecond = timer.get();
		}
		
		
		if(leftInputJump || Math.abs(leftInputHistory.element() - leftInput) > jumpThreshold) {
			if(!leftInputJump) {
				leftInputJump = true;
				leftPrevious = leftInputHistory.element();
			}
			if(leftInputJump && jumpThreshold*)
		}
		//Need to add a sloping function and handle both sides of robot
		
		
		/* drive the robot, when driving forward one side will be red.  
		 * This is because DifferentialDrive assumes 
		 * one side must be negative */
		
		// System.out.println("Joy0Y:" + joy.getRawAxis(1) + "Joy1Y:" + joy.getRawAxis(3));
		// USE A FRICKIN WHILE TRUE ALONG WITH A GREATER AND LESS CASE FOR ERROR HANDLING AND STUFF
		while (panel.getVoltage() >= RobotMap.voltageDropThreshold) {
			drive.tankDrive(-(1) * (speedL)*OI.j.getRawAxis(1), -(1) * (speedR)*OI.j.getRawAxis(3));
			
			if (panel.getVoltage() <= RobotMap.voltageDropThreshold) {
				drive.tankDrive(-(0.25) * (speedL) * OI.j.getRawAxis(1), -(0.25) * (speedL) * OI.j.getRawAxis(3));
			}
		}
		
//		timer.start();
//		solenoidOne.set(DoubleSolenoid.Value.kForward);
		
//		mainCompressor.start();
//		while (timer.get() <= 30) {
//			if (timer.get() >= 30) {
//				solenoidOne.set(DoubleSolenoid.Value.kOff);
//				solenoidOne.set(DoubleSolenoid.Value.kForward);
//				break;
//			}
//		}
		
		// float leftVal = leftRawData.getNumber(DEFAULT_DRIVE).floatValue();
		// float rightVal = rightRawData.getNumber(DEFAULT_DRIVE).floatValue();
	}
}