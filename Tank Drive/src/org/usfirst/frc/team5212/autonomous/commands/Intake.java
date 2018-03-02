package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command {

	public Intake() {
		requires(Robot.cubeIO); // create new subsystem for intake motor control
		setTimeout(5);
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
		Robot.cubeIO.in();
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
		end();
	}

}
