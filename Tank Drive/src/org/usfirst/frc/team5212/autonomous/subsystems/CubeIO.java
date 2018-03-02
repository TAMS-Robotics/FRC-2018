package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CubeIO extends Subsystem {
	Spark leftArm = null;
	Spark rightArm = null;
	
	public CubeIO() {
		super("CubeIO"); // garbage values - change these later
		leftArm = new Spark(0);
		rightArm = new Spark(1);
	}


	@Override
	protected void initDefaultCommand() {
	}
	
	/*
	 * Runs the arms such that a cube
	 * would be pulled into the robot
	 */
	public void in() {
		leftArm.setSpeed(RobotMap.leftArmIntakeSpeed);
		rightArm.setSpeed(RobotMap.rightArmIntakeSpeed);
	}
	
	/*
	 * Runs the arms such that a cube
	 * would be ejected from the robot
	 */
	public void out() {
		leftArm.setSpeed(RobotMap.leftArmOutputSpeed);
		rightArm.setSpeed(RobotMap.rightArmOutputSpeed);
	}
}
