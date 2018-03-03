package org.usfirst.frc.team5212.autonomous.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Drive extends CommandGroup {
	public Drive() {
		addSequential(new EncoderDrive());
		addSequential(new EncoderTurn());
	}
}
