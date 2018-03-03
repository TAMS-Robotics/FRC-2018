package org.usfirst.frc.team5212.autonomous.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team5212.robot.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DriveBase extends Subsystem {
	
	WPI_TalonSRX frontL = new WPI_TalonSRX(RobotMap.frontLeftPort);
	WPI_TalonSRX frontR = new WPI_TalonSRX(RobotMap.frontRightPort);
	WPI_TalonSRX leftSlave1 = new WPI_TalonSRX(RobotMap.leftSlave1Port);
	WPI_TalonSRX rightSlave1 = new WPI_TalonSRX(RobotMap.rightSlave1Port);
	WPI_TalonSRX leftSlave2 = new WPI_TalonSRX(RobotMap.leftSlave2Port);
	WPI_TalonSRX rightSlave2 = new WPI_TalonSRX(RobotMap.rightSlave2Port);
	
	DifferentialDrive drive = new DifferentialDrive(frontL, frontR);
		
	final double speedL = 1.0;
	final double speedR= 1.0;
	
	public DriveBase() {
		leftSlave1.follow(frontL);
		rightSlave1.follow(frontR);
		leftSlave2.follow(frontL);
		rightSlave2.follow(frontR);
	}
	@Override
	protected void initDefaultCommand() {
		// setDefaultCommand(new EncoderDrive());
	}
	
	public void forward() {
		drive.tankDrive((-1 * speedL), (-1 * speedR));
	}
	

}
