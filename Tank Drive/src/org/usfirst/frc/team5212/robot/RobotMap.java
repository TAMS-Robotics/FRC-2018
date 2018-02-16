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
	
	public static final int pdpPort = 0;
	
	public static final double voltageDropThreshold = 7.0;
}
