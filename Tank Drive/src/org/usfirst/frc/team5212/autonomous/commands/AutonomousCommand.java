package org.usfirst.frc.team5212.autonomous.commands;

import edu.wpi.first.wpilibj.command.*;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand() {
		addParallel(new EncoderDrive());
		addParallel(new EncoderTurn());
		addSequential(new PrepareShoot());
		addSequential(new Shoot());
	}
}
