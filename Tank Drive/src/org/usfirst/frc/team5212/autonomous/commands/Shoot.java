package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {

	public Shoot() {
		super ("Shoot");
		requires(Robot.pneum);
	}
	
	protected void initialize() {
	}
	
	protected void execute() {
		Robot.pneum.shoot();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
//		Robot.pneum.lock();
	}
		
	protected void interrupted() {
		end();
	}

}
