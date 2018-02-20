package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;
import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {

	Pneumatics pneum = new Pneumatics();
	public Shoot() {
		super ("Shoot");
		requires(Robot.pneum);
	}
	
	protected void initialize() {
		Robot.pneum.shoot();
	}
	
	protected void execute() {
	}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		Robot.pneum.lock();
	}
		
	protected void interrupted() {
		end();
	}

}
