package org.usfirst.frc.team5212.autonomous.subsystems;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team5212.autonomous.commands.PrepareShoot;
import org.usfirst.frc.team5212.robot.*;

public class Pneumatics extends Subsystem {
	
	Compressor main = new Compressor(RobotMap.compressorPort);
	//	DoubleSolenoid solenoidOne = new DoubleSolenoid(0, 1);

	boolean isEnabled = main.enabled();
	boolean pressureSwitch = main.getPressureSwitchValue();
	double current = main.getCompressorCurrent();
	// or we could do something related to Robot.somethingdu? 

	@Override
	protected void initDefaultCommand() {
		System.out.println("Compressor init");
		main.setClosedLoopControl(true);

		main.stop();

	}

	public void compressAir()
	{
		System.out.println("Compressor compressing");
		main.start();
	}

	public void stopCompress()
	{
		main.stop();
	}

	/*	public void shootMyKintama () throws InterruptedException
	{

		solenoidOne.set(DoubleSolenoid.Value.kForward);

		solenoidOne.wait(5000);
	}

	public void stopShooting ()
	{
		solenoidOne.set(DoubleSolenoid.Value.kOff);
	}*/
}
