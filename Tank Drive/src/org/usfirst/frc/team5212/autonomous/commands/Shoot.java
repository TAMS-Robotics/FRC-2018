package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;

import edu.wpi.first.wpilibj.command.Command;

public class Shoot extends Command {

	Pneumatics pneum = new Pneumatics();
	public Shoot() {
		super ("Shoot");
		requires(pneum);
	}
	
	protected void initialize() {
	}
	
	protected void execute() {
			//pneum.shootMyKintama();
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		//pneum.stopShooting();
	}
		
	protected void interrupted() {
	}

}
