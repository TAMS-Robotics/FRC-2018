package org.usfirst.frc.team5212.autonomous.subsystems;


import java.util.ArrayDeque;
import java.util.Queue;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends PIDSubsystem {

	public final WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(RobotMap.frontLeftPort);
	public final WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(RobotMap.frontRightPort);
	public final WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftSlave1Port);
	public final WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightSlave1Port);
	public final WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(RobotMap.leftSlave2Port);
	public final WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(RobotMap.rightSlave2Port);


	public final Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public final Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k4X);

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
		super("DriveTrain", RobotMap.driveMotorKp, RobotMap.driveMotorKi, RobotMap.driveMotorKd);

		//	frontLeftMotor.setInverted(false);
		//	frontRightMotor.setInverted(false);

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

		for (double i = 0; i < (historyLength / updateFrequency); i++) {
			leftInputHistory.add(0.);
			rightInputHistory.add(0.);
		}

		jumpThreshold = 1.0 / 2;

		leftInputJump = false;
		rightInputJump = false;

		// The constructor passes a name for the subsystem 
		// and the P, I, D constants that are used when computing motor output
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);

		leftEncoder.setDistancePerPulse(RobotMap.distancePerPulse);
		rightEncoder.setDistancePerPulse(RobotMap.distancePerPulse);

		leftEncoder.reset();
		rightEncoder.reset();
	}

	@Override
	protected void initDefaultCommand() {

	}

	protected double returnPIDInput() {
		return leftEncoder.getDistance()-rightEncoder.getDistance(); // returns the sensor value that is providing the feedback for the system
	}

	protected void usePIDOutput(double output) {
		Robot.drivetrain.frontLeftMotor.pidWrite(output);
		Robot.drivetrain.frontRightMotor.pidWrite(output); // this is where the computed output value from the PIDController is applied to the motor
	}


	public void reverseOrientaion() {
		// temp-based swap
		int joystickSwap = RobotMap.leftJoystickPort;
		RobotMap.leftJoystickPort = RobotMap.rightJoystickPort;
		RobotMap.rightJoystickPort = joystickSwap;

		if (frontLeftMotor.getInverted() && frontRightMotor.getInverted()) {
			System.out.println("Front is now facing the launcher");

			frontLeftMotor.setInverted(false);
			frontRightMotor.setInverted(false);

			leftSlave1.setInverted(false);
			leftSlave2.setInverted(false);

			rightSlave1.setInverted(false);
			rightSlave2.setInverted(false);
		}
		else {
			System.out.println("Front is now the climber");

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
		drive.tankDrive(-1*leftInput, -1*rightInput);
	}

	public void slewTankDrive(double leftInput, 
			double rightInput, 
			double panelVoltage) {

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

			// If there was a jump noticed, use a linear function to 
			if (leftInputJump) {

				// currently using a linear function
				functionOutput = leftJumpDirection * 
						jumpThreshold * 
						(timer.get() - leftJumpTime) + .5 * 
						leftJumpDirection;

				/* Does this code not do the same thing regardless of 
				 * the evaulation of the conditional
				 * (functionOutput < leftInput)?
				 */
				if (leftJumpDirection == 1) {
					if (functionOutput < leftInput) {
						// REMOVE WHEN FINISHED DEBUGGING
						//						System.out.println(functionOutput);
						leftInput = functionOutput;
					}
					else {
						leftInputJump = false;
					}
				}
				else {
					if (functionOutput > leftInput) {
						// REMOVE WHEN FINISHED DEBUGGING
						//						System.out.println(functionOutput);
						leftInput = functionOutput;
					}
					else {
						leftInputJump = false;
					}
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
						(timer.get() - rightJumpTime) + .5 * 
						rightJumpDirection;

				if (rightJumpDirection == 1) {
					if (functionOutput < rightInput) {
						// REMOVE WHEN FINISHED DEBUGGING
						//						System.out.println(functionOutput);
						rightInput = functionOutput;
					}
					else {
						rightInputJump = false;
					}
				}
				else {
					if(functionOutput > rightInput) {
						// REMOVE WHEN FINISHED DEBUGGING
						//						System.out.println(functionOutput);
						rightInput = functionOutput;
					}
					else {
						rightInputJump = false;
					}
				}
			}

		}

		//Need to add a sloping function and handle both sides of robot


		/* drive the robot, when driving forward one side will be red.  
		 * This is because DifferentialDrive assumes 
		 * one side must be negative */

		double leftDriveValue = (-1 * (RobotMap.speedL) * leftInput);
		double rightDriveValue = (-1 * (RobotMap.speedR) * rightInput);

		if (panelVoltage >= RobotMap.voltageDropThreshold) {
			//			System.out.println(leftDriveValue+" "+rightDriveValue);
			drive.tankDrive(leftDriveValue, rightDriveValue);

		}
		else {
			//			System.out.println(leftDriveValue+" "+rightDriveValue);

			drive.tankDrive(0.25 * leftDriveValue, 0.25  * rightDriveValue);
		}
	}


	public double[] joystickBalance(double leftInput, double rightInput) {

		// calculate L:R ratios of joystick and wheels 
		double joystickRatio = leftInput / rightInput;
		double speedRatio = leftEncoder.getRate() / rightEncoder.getRate();

		// get the difference between ratios and calculate sigmoid value for adjustment
		double joystickWheelRatioDifferential = joystickRatio - speedRatio;
		double sigmoidJoystickWheelRatioDifferential = sigmoid(joystickWheelRatioDifferential);

		double leftOutput, rightOutput;

		// adjust motor values by sigmoid difference 
		// joysticks are ahead of the wheels
		leftOutput = leftInput + (sigmoidJoystickWheelRatioDifferential / 2); 
		rightOutput = rightInput - (sigmoidJoystickWheelRatioDifferential / 2);

		// scale down powers by the larger one greater than 1, if it exists
		if (leftOutput > 1) {
			leftOutput /= leftOutput;
			rightOutput /= leftOutput;
		}
		else if (rightOutput > 1) {
			rightOutput /= rightOutput;
			leftOutput /= rightOutput;
		}

		double[] returnable = {leftOutput, rightOutput};

		return returnable;

	}


	public double[] driveStraight(double leftInput, double rightInput) {
		double leftRightDiff = leftEncoder.getRate() - rightEncoder.getRate();
		double sigDiff = sigmoid(leftRightDiff);

		double rightOutput = rightInput + sigDiff;
		double leftOutput = leftInput - sigDiff;

		// scale down powers by the larger one greater than 1, if it exists
		if (leftOutput > 1) {
			leftOutput /= leftOutput;
			rightOutput /= leftOutput;
		}
		else if (rightOutput > 1) {
			rightOutput /= rightOutput;
			leftOutput /= rightOutput;
		}

		double[] returnable = {leftOutput, rightOutput};

		return returnable;
	}


	// 1 / (1 + Math.exp(-x))
	private double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}


}