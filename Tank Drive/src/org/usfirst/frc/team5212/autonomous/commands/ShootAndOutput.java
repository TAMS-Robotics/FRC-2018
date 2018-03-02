package org.usfirst.frc.team5212.autonomous.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootAndOutput extends CommandGroup {
	public ShootAndOutput() {
		addParallel(new Shoot());
		addParallel(new Output());
	}
}
