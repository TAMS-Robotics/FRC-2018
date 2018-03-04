package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Output extends Command {

	public Output() {
		requires(Robot.cubeIO); 
		setTimeout(5);
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		Robot.cubeIO.out();
	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	@Override
	protected void end() {
		Robot.cubeIO.stop();
	}
	
	@Override
	protected void interrupted() {
		end();
	}

}
