package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/* Extends PIDSubsytem */
public class PIDDrive extends PIDSubsystem { 
	public Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

	/*
	 * leftEncoder.setMaxPeriod(.1);
	 * leftEncoder.setMinRate(10);
	 * leftEncoder.setRe\verseDirection(true);
	 * leftEncoder.setSamplesToAverage(7);
	 * 
	 */

	public PIDDrive() {
		super("PIDDrive", 
				RobotMap.frontLeftMotorKp, 
				RobotMap.frontLeftMotorKi, 
				RobotMap.frontLeftMotorKd);
		
		// The constructor passes a name for the subsystem 
		// and the P, I and D constants that are used for computing motor output
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
	}

	public void initDefaultCommand() {
		
	}

	protected double returnPIDInput() {
		// returns sensor value that's providing system feedback
		return (leftEncoder.getDistance() - rightEncoder.getDistance());
	}

	protected void usePIDOutput(double output) {
		
		Robot.drivetrain.frontLeftMotor.pidWrite(output);
		
		// this is where PIDController computed output value is applied to motor
		Robot.drivetrain.frontRightMotor.pidWrite(output); 
	}
}