package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderDrive extends Command {
	
	public EncoderDrive() {
		super("EncoderDrive");
		requires(Robot.drivetrain);
		setTimeout(5);
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
		return isTimedOut();
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
