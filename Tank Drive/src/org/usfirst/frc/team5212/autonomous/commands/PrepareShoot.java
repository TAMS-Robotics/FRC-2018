package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PrepareShoot extends Command {

	public PrepareShoot() {
		super("PrepareShoot");
		requires(Robot.pneum);
		setTimeout(5);
	}

	protected void initialize() {
		System.out.println("compress init");
		Robot.pneum.compress();
	}

	protected void execute() {
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		Robot.pneum.stopCompress();
	}

	protected void interrupted() {
		end();
	}
}
