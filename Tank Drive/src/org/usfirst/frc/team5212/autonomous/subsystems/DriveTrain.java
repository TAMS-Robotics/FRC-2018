package org.usfirst.frc.team5212.autonomous.subsystems;


import java.util.ArrayDeque;
import java.util.Queue;

import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem {
	
	public WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftPort);
	public WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightPort);
	public WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftSlave1Port);
	public WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightSlave1Port);
	public WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(RobotMap.leftSlave2Port);
	public WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(RobotMap.rightSlave2Port);
	
	int joystickSwap;
		
	DifferentialDrive drive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
	
	// keeps track of the time
	Timer timer;
	
	// constant storing the threshold value of the input in input units (0-255) per second
	double jumpThreshold;
	
	// time at which the last jump was logged
	double leftJumpTime;
	double rightJumpTime;
	
	// stores the input before the last jump
	double leftPrevious;
	double rightPrevious;
	
	// store the function output
	double functionOutput;
	
	// frequency for input logging
	double updateFrequency;
	
	// length of time to look back for input jumps
	double historyLength;
	
	// keep track of a positive or negative jump
	int leftJumpDirection;
	int rightJumpDirection;
	
	// trigger for tracking if a jump has been detected
	boolean leftInputJump;
	boolean rightInputJump;
	
	// Keeps the input history for the last second
	Queue<Double> leftInputHistory;
	Queue<Double> rightInputHistory;
		
	public DriveTrain() {
//		frontLeftMotor.setInverted(false);
//		frontRightMotor.setInverted(false);
		
		leftSlave1.follow(frontLeftMotor);
		rightSlave1.follow(frontRightMotor);
		leftSlave2.follow(frontLeftMotor);
		rightSlave2.follow(frontRightMotor);
		
		timer = new Timer();
		timer.start();
		historyLength  = 1;
		updateFrequency = .05;
		
		leftInputHistory = new ArrayDeque<Double>();
		rightInputHistory = new ArrayDeque<Double>();
		
		for (double i = 0; i < (historyLength/updateFrequency); i++) {
			leftInputHistory.add(0.);
			rightInputHistory.add(0.);
		}
		
		jumpThreshold = 1.0 / 2;
		
		leftInputJump = false;
		rightInputJump = false;
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}
	
	public void reverseOrientaion() {
		// temp-based swap
		joystickSwap = RobotMap.leftJoystickPort;
		RobotMap.leftJoystickPort = RobotMap.rightJoystickPort;
		RobotMap.rightJoystickPort = joystickSwap;
		
		if (frontLeftMotor.getInverted() && frontRightMotor.getInverted()) {
			frontLeftMotor.setInverted(false);
			frontRightMotor.setInverted(false);
			
			leftSlave1.setInverted(false);
			leftSlave2.setInverted(false);
			
			rightSlave1.setInverted(false);
			rightSlave2.setInverted(false);
		}
		else {
			frontLeftMotor.setInverted(true);
			frontRightMotor.setInverted(true);
			
			leftSlave1.setInverted(true);
			leftSlave2.setInverted(true);
			
			rightSlave1.setInverted(true);
			rightSlave2.setInverted(true);
		}
	}
	
	public void forward() {
		drive.tankDrive(-1 * RobotMap.speedL, -1 * RobotMap.speedR);
	}
	
	public void tankDrive(double leftInput, double rightInput) {
		drive.tankDrive(leftInput, rightInput);
	}
	
	public void slewTankDrive(double leftInput, double rightInput, double panelVoltage) {
		
		// Logs joystick inputs every updateFrequency seconds
		if (((int)(timer.get() / updateFrequency) % 2) == 1) {
			
			leftInputHistory.remove();
			leftInputHistory.add(leftInput);
			
			rightInputHistory.remove();
			rightInputHistory.add(rightInput);
		}
		
		/*
		 * Checks for input jumps
		 * increases the output power of the motor
		 * limits them to a linear funciton
		 */
		if (leftInputJump || 
				(
						(Math.abs(leftInputHistory.element() - leftInput) > jumpThreshold) && 
						(Math.abs(leftInput) > .5)
				)
			) 
		{
			if (!leftInputJump) {
				/* notify that there has been a jump */
				leftInputJump = true;
				
				/* log the jump time */
				leftJumpTime = timer.get();
				leftPrevious = leftInputHistory.element();
				
				/* log which direction the jump was */
				leftJumpDirection = ((leftInputHistory.element() - leftInput) > 0) ? -1 : 1; 
			}
			/* If there was a jump noticed, use a linear function to */
			if (leftInputJump) {
				/* currently using a linear function */
				functionOutput = leftJumpDirection * 
						jumpThreshold * 
						((timer.get() - leftJumpTime) + .5) * 
						leftJumpDirection;
				
				if (leftJumpDirection == 1) {
					leftInput = (functionOutput < leftInput) ? functionOutput : 0;
				}
				else {
					leftInput = (functionOutput > leftInput) ? functionOutput : 0;
				}
			}
			
		}
		
		// Checks for input jumps that increase the output power of the motor and limit them to a linear function
		if (rightInputJump || 
				(
						((Math.abs(rightInputHistory.element() - rightInput)) > jumpThreshold) && 
						(Math.abs(rightInput) > .5)
				)
			) 
		{
			if (!rightInputJump) {
				// notify that there has been a jump
				rightInputJump = true;
				
				// log the jump time
				rightJumpTime = timer.get();
				rightPrevious = rightInputHistory.element();
				
				// log which direction the jump was
				rightJumpDirection = ((rightInputHistory.element() - rightInput) > 0) ? -1 : 1;
			}
			
			// If there was a jump noticed, use a linear function to 
			if (rightInputJump) {
				// currently using a linear function
				functionOutput = rightJumpDirection * 
						jumpThreshold * 
						((timer.get() - rightJumpTime) + .5) * 
						rightJumpDirection;
				
				if (rightJumpDirection == 1) {
					rightInput = (functionOutput < rightInput) ? functionOutput : 0;
				}
				else {
					rightInput = (functionOutput > rightInput) ? functionOutput : 0;
				}
			}
			
		}
		
		// Need to add a sloping function and handle both sides of robot
		
		
		/* drive the robot, when driving forward one side will be red.  
		 * This is because DifferentialDrive assumes 
		 * one side must be negative */
		
		if (panelVoltage >= RobotMap.voltageDropThreshold) {
			drive.tankDrive(
					((-1) * (RobotMap.speedL) * leftInput), 
					((-1) * (RobotMap.speedR) * rightInput)
					);
		}
		else {
			drive.tankDrive(
					((-0.25) * (RobotMap.speedL) * leftInput), 
					((-0.25) * (RobotMap.speedR) * rightInput)
					);
		}
	}
}