package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReverseOrientation extends Command {

	public ReverseOrientation() {
		super("ReverseOrientaion");
		requires(Robot.drivetrain);
	}
	protected void initialize() {
		System.out.println("Reversing orientation");
	}
	protected void execute() {
		System.out.println("Actually reversing");
		Robot.drivetrain.reverseOrientaion();
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
	}

	protected void interrupted() {
	}
}
