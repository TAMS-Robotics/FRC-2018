package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimberUp extends Command {

	public ClimberUp() {
		super ("ClimberUp");
		requires(Robot.pneum);
	}

	protected void initialize() {

	}

	protected void execute() {
		Robot.pneum.climberUp();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		// Robot.pneum.lock();
		// we don't want to lock
	}

	protected void interrupted() {
		end();
	}

}
