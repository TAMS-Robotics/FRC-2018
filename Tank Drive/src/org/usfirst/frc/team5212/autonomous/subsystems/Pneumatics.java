package org.usfirst.frc.team5212.autonomous.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5212.robot.*;

public class Pneumatics extends Subsystem {
	
//	Compressor compressor = new Compressor(RobotMap.compressorPort);
	//DoubleSolenoid solenoid1 = new DoubleSolenoid(RobotMap.solenoidPortOne, RobotMap.solenoidPortTwo);

	@Override
	protected void initDefaultCommand() {
		System.out.println("Compressor init");
//		compressor.setClosedLoopControl(false);
	}

	public void compress()
	{
		System.out.println("Compressor compressing");
//		compressor.setClosedLoopControl(true);
	}

	public void stopCompress()
	{
//		compressor.setClosedLoopControl(false);
	}

	public void shoot()
	{
		//solenoid1.set(DoubleSolenoid.Value.kForward);
	}

	public void reset()
	{
		//solenoid1.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lock() {
		//solenoid1.set(DoubleSolenoid.Value.kOff);
	}
}
