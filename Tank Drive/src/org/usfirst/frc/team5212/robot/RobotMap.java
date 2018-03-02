/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5212.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	public static final int frontLeftPort = 0;
	public static final int frontRightPort = 2;
	public static final int leftSlave1Port = 1;
	public static final int rightSlave1Port = 3;
	public static final int leftSlave2Port = 5;
	public static final int rightSlave2Port = 4;
	        
	public static final int TIMEOUT = 2;
	        
	public static final int compressorPort = 0;
	public static final int solenoidPortOne = 0;
	public static final int solenoidPortTwo = 1;
	        
	public static final int pdpPort = 0;
	        
	public static final double voltageDropThreshold = 8.0;
	public static final double speedR = 1.;
	public static final double speedL = 1.;
	
	// not final 
	// needs to be assigned in DriveTrain.java
	// dont make this final amogh u idiot
	public static int leftJoystickPort = 1;
	public static int rightJoystickPort = 3;
            
	public static final int ioLeftArmPort = 0; // test
	public static final int ioRightArmPort = 1; // test
	
	public static final int leftArmIntakeSpeed = 1;
	public static final int rightArmIntakeSpeed = 1;
	
	public static final int leftArmOutputSpeed = -1;
	public static final int rightArmOutputSpeed = -1;
	
	
	public static final int xButton = 1;
	public static final int yButton = 4;
	public static final int aButton = 2;
	public static final int bButton = 3;
	public static final int lbButton = 5;
	public static final int rbButton = 6;
	public static final int ltButton = 7;
	public static final int rtButton = 8;
	        
	public static final double driveMotorKp = 2.0;
	public static final double driveMotorKi = 0.0;
	public static final double driveMotorKd = 0.0;
	
	public static final double  armMotorKp = 2.0;
	public static final double  armMotorKi = 0.0;
	public static final double  armMotorKd = 0.0;

	
	public static final double distancePerPulse = Math.PI * 6 / 12 / 2048;

}
