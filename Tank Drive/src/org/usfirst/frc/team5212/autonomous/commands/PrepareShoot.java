package org.usfirst.frc.team5212.autonomous.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team5212.robot.*;

import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;

public class PrepareShoot extends Command {

	public PrepareShoot() {
		super("PrepareShoot");
//		requires(Robot.pneum);
	}

	protected void initialize() {
		System.out.println("command init");
		setTimeout(5);
	}

	protected void execute() {
		System.out.println("Trying to compress");
//		Robot.pneum.compressAir();
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
		if (isFinished()) {
//			Robot.pneum.stopCompress();
		}
	}

	protected void interrupted() {
		end();
	}
}
