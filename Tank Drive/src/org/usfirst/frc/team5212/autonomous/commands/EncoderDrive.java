package org.usfirst.frc.team5212.autonomous.commands;

import java.awt.Robot;

import org.usfirst.frc.team5212.autonomous.subsystems.DriveBase;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class EncoderDrive extends Command {
	
	DriveBase drivebase;
	double speedL;
	double speedR;
	public EncoderDrive() {
		super("EncoderDrive");
		requires(drivebase);
	}
	
	// called once when this Command is initialized
	protected void initialize() {
		speedL = 1;
		speedR = 1;
	}
	
	// called repeatedly in Autonomous mode
	protected void execute() {
		// TODO Auto-generated method stub
		
		
	}

	// returns true when method no longer needs to execute
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	// called once after isFinished() is called
	protected void end() {
		
	}
		
	// called when another Command interrupts the execution of this Command
	// we'll just call the end
	protected void interrupted() {
		end();
	}
}
