package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CubeIO extends Subsystem {
	Spark leftArm = new Spark(0);
	Spark rightArm = new Spark(1);
	
	public CubeIO() {
		super("CubeIO"); 
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	public void in() {
		leftArm.setSpeed(RobotMap.leftArmIntakeSpeed);
		rightArm.setSpeed(RobotMap.rightArmIntakeSpeed);
	}
	
	public void out() {
		leftArm.setSpeed(RobotMap.leftArmOutputSpeed);
		rightArm.setSpeed(RobotMap.rightArmOutputSpeed);
	}
	
	public void stop() {
		leftArm.setSpeed(0);
		rightArm.setSpeed(0);
	}
}
