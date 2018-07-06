package org.usfirst.frc.team5212.autonomous.subsystems;


import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor compressor = new Compressor(RobotMap.compressorPort);
	
	DoubleSolenoid solenoidShooter = new DoubleSolenoid(RobotMap.shooterPortOne, 
			RobotMap.shooterPortTwo);
	DoubleSolenoid solenoidClimber = new DoubleSolenoid(RobotMap.climberPortOne, 
			RobotMap.climberPortTwo);

	@Override
	protected void initDefaultCommand() {
		System.out.println("Compressor init");
		
		compressor.start();
		
		System.out.println("Compressor in a closed loop - jolly good");
		solenoidShooter.set(DoubleSolenoid.Value.kForward);
		solenoidClimber.set(DoubleSolenoid.Value.kReverse);
	}

	public void compress() {
		System.out.println("Compressor compressing in a compressed manner (that's compressive)");
	}

	public void stopCompress() {
		System.out.print("Stopping compression");
		compressor.setClosedLoopControl(false);
	}
	
	public void shoot() {
		solenoidShooter.set(DoubleSolenoid.Value.kForward);
	}

	public void reset() {
		solenoidShooter.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lock() {
		solenoidClimber.set(DoubleSolenoid.Value.kOff);
		solenoidShooter.set(DoubleSolenoid.Value.kOff);
	}
	
	public void climberUp() {
		solenoidClimber.set(DoubleSolenoid.Value.kForward);
	}
	
	public void climberDown() {
		solenoidClimber.set(DoubleSolenoid.Value.kReverse);
	}
}
