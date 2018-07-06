package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CubeIO extends Subsystem {
	public Spark leftBackArm = new Spark(1);
	public Spark rightBackArm = new Spark(0);
	public Spark leftFrontArm = new Spark(3);
	public Spark rightFrontArm = new Spark(2);
	
	public CubeIO() {
		super("CubeIO"); 
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	public void in() {
		leftBackArm.setSpeed(RobotMap.leftArmIntakeSpeed);
		rightBackArm.setSpeed(RobotMap.rightArmIntakeSpeed);
		leftFrontArm.setSpeed(RobotMap.leftArmIntakeSpeed);
		rightFrontArm.setSpeed(RobotMap.rightArmIntakeSpeed);
	}
	
	public void out() {
		leftBackArm.setSpeed(RobotMap.leftArmOutputSpeed);
		rightBackArm.setSpeed(RobotMap.rightArmOutputSpeed);
		leftFrontArm.setSpeed(RobotMap.leftArmOutputSpeed);
		rightFrontArm.setSpeed(RobotMap.rightArmOutputSpeed);	
	}
	
	public void stop() {
		leftBackArm.setSpeed(0);
		rightBackArm.setSpeed(0);
		leftFrontArm.setSpeed(0);
		rightFrontArm.setSpeed(0);
	}
}
