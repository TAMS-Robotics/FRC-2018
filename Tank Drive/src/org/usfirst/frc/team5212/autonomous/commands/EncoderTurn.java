package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderTurn extends Command {
	
	public EncoderTurn() {
		requires(Robot.drivetrain);
		setTimeout(5);
	}
	
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	protected void execute() {
		
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
	
	}
		
	protected void interrupted() {
		end();
	}
}
