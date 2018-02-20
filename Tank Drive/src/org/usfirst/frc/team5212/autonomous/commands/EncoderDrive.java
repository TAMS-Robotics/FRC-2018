package org.usfirst.frc.team5212.autonomous.commands;


import org.usfirst.frc.team5212.autonomous.subsystems.DriveBase;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class EncoderDrive extends Command {
	
	public EncoderDrive() {
		
		setTimeout(2.0);
		
	}

	// called once when this Command is initialized
	protected void initialize() {
	}

	// called repeatedly in Autonomous mode
	protected void execute() {
		
	}

	// returns true when method no longer needs to execute
	protected boolean isFinished() {
		return false;
	}

	// called once after isFinished() is called
	protected void end() {

	}

	// called when another Command interrupts the execution of this Command
	// we'll just call the end
	protected void interrupted() {
		end();
	}
}
