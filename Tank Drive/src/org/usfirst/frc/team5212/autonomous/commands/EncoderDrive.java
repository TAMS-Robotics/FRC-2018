package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderDrive extends Command{

	public EncoderDrive() {
		super("EncoderDrive");
		requires(Robot.drivetrain);
		setTimeout(5);
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override 
	protected void end() {
		
	}
	
	@Override
	protected void interrupted() {
		this.end();
	}
}
