package org.usfirst.frc.team5212.autonomous.subsystems;


import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Pneumatics extends Subsystem {
	
	Compressor compressor = new Compressor(RobotMap.compressorPort);
	DoubleSolenoid solenoid1 = new DoubleSolenoid(RobotMap.solenoidPortOne, RobotMap.solenoidPortTwo);

	@Override
	protected void initDefaultCommand() {
		System.out.println("Compressor init");
		compressor.setClosedLoopControl(true);
		System.out.println("Compressor in a closed loop - jolly good");
	}

	public void compress() {
		System.out.println("Compressor compressing in a compressed manner (that's compressive)");
	}

	public void stopCompress() {
		compressor.setClosedLoopControl(false);
		compressor.stop();
	}

	public void shoot() {
		solenoid1.set(DoubleSolenoid.Value.kForward);
	}

	public void reset() {
		solenoid1.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lock() {
		solenoid1.set(DoubleSolenoid.Value.kOff);
	}
}
