package org.usfirst.frc.team5212.autonomous.commands;

import java.awt.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderDrive extends Command {

	public EncoderDrive() {
		requires(null); // change this - we need to figure out what we need to call in requires
	}
	
	// called once when this Command is initialized
	protected void initialize() {
		
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
