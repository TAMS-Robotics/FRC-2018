package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberDown extends Command {

	public ClimberDown() {
		super ("ClimberDown");
		requires(Robot.pneum);
	}
	
	protected void initialize() {
		
	}
	
	protected void execute() {
		Robot.pneum.climberDown();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		// stays instead of locking
	}
		
	protected void interrupted() {
		end();
	}

}
