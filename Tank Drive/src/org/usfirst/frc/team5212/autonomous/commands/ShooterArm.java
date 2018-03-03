package org.usfirst.frc.team5212.autonomous.commands;


import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterArm extends PIDSubsystem {
	
	// declare final const in RobotMap = MAX_HEIGHT
	
	public WPI_TalonSRX left = null; 
	public WPI_TalonSRX right = null; 
	public PIDController pid;
	
	public ShooterArm() {
		super("ShooterArm", RobotMap.shooterKp,
				RobotMap.shooterKi,
				RobotMap.shooterKd);
		left = new WPI_TalonSRX(RobotMap.leftShooterArm);
		right = new WPI_TalonSRX(RobotMap.rightShooterArm); // this one has encoder
	}

	public void up() {
		
	}
	
	public void down() {
		
	}
	
	public void runPID(double targetPos) {
		pid.disable();
		pid.setSetpoint(targetPos);
		pid.enable();
	}
	
	public void setPower(double power) { 
		pid.disable();
		right.set(power);
		left.set(power);
	}
	
	public double getEncoderVal() {
		return right.get();
	}
	
	@Override
	protected void initDefaultCommand() {
		
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
}
