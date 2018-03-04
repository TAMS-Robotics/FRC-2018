/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5212.robot;

/*
 * TABLE OF CONTENTS
 * 1. Project Wide States/Constants/Ports
 * 		a. states
 * 		b. machine ports
 * 		c. miscellaneous assignments
 * 2. DriveTrain
 * 		a. ports
 * 		b. constants
 * 3. ShooterArm
 * 		a. ports
 * 4. Pneumatics
 * 		a. ports
 * 5. CubeIO
 * 		a. ports
 * 		b. constants
 * 6. Buttons
 * 7. PID Constants
 * 		a. driving
 * 		b. shooting
 * 		c. arms
 * 8. Miscellaneous
 */

public class RobotMap {
	// project wide states - safety purposes
	public static final boolean DRIVE_TRAIN_ENABLED = false;
	public static final boolean CLIMBER_ENABLED = false;
	public static final boolean SHOOTER_ENABLED = false;
	
	// machine ports
	public static final int compressorPort = 0;
	public static final int pdpPort = 0;
	
	// not final 
	// needs to be assigned in DriveTrain.java
	// dont make this final amogh u idiot
	public static int leftJoystickPort = 1;
	public static int rightJoystickPort = 3;
	
	/* DriveTrain subsystem*/
	// ports
	public static final int frontLeftPort = 2;
	public static final int frontRightPort = 7;
	public static final int leftSlave1Port = 3;
	public static final int rightSlave1Port = 8;
	public static final int leftSlave2Port = 4;
	public static final int rightSlave2Port = 9;
	
	// constants
	public static final double voltageDropThreshold = 8.0;
	public static final double speedR = 1.;
	public static final double speedL = 1.;
	
	
	/* Pneumatics subsystem */
	// ports
	public static final int shooterPortOne = 6;
	public static final int shooterPortSingle = 7;
	public static final int shooterPortTwo = 1;
	public static final int climberPortOne = 2;
	public static final int climberPortTwo = 3;
	
    
	/* ShooterArm subsystem */
	// ports
	public static final int leftShooterArm = 6;
	public static final int rightShooterArm = 5;
	
	/* CubeIO Subsystem */
	// ports
	public static final int ioLeftArmPort = 0;
	public static final int ioRightArmPort = 1;  
	
	// constants
	public static final int leftArmIntakeSpeed = -1;
	public static final int rightArmIntakeSpeed = 1;
	
	public static final int leftArmOutputSpeed = 1;
	public static final int rightArmOutputSpeed = -1;
	
	// buttons
	public static final int xButton = 1;
	public static final int yButton = 4;
	public static final int aButton = 2;
	public static final int bButton = 3;
	public static final int lbButton = 5;
	public static final int rbButton = 6;
	public static final int ltButton = 7;
	public static final int rtButton = 8;
	public static final int stopButton = 9;
	public static final int startButton = 10;
	
	/* PID constants */
	
	// driving motors
	public static final double frontLeftMotorKp = 2.0;
	public static final double frontLeftMotorKi = 0.0;
	public static final double frontLeftMotorKd = 0.0;	
	
	public static final double driveMotorKp = 2.0;
	public static final double driveMotorKi = 0.0;
	public static final double driveMotorKd = 0.0;
	
	// shooting motors
	public static final double shooterKp = 1.2;
	public static final double shooterKi = 0.7;
	public static final double shooterKd = 0.3;	
	
	// arms motors
	public static final double  armMotorKp = 0.4;
	public static final double  armMotorKi = 0.0;
	public static final double  armMotorKd = 0.0;
	
	// miscellaneous constants
	public static final double distancePerPulse = Math.PI * 6 / 12 / 2048;
}
