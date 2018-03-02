package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


public class EncoderDrive extends Command {
	
	boolean reachedThreeQuartersPoint, reachedEndPoint;
	
	public EncoderDrive() {
		super("EncoderDrive");
		requires(Robot.drivetrain);
		setTimeout(5);
	}
	
	// called once when this Command is initialized
	protected void initialize() {
		Robot.drivetrain.leftEncoder.reset();
		Robot.drivetrain.rightEncoder.reset();
	}
	
	// called repeatedly in Autonomous mode
	protected void execute() {
		// drive forward certain distance to point
		reachedThreeQuartersPoint = false; // get data from vision (.getCurrentDistance() < .75 * DISTANCE_TO_TRAVEL)
		reachedEndPoint = false; // get data from vision (.getCurrentDistance() < DISTANCE_TO_TRAVEL)
		
		while (!String.valueOf(reachedThreeQuartersPoint).equals("true")) {
			Robot.drivetrain.tankDrive(-0.6 * RobotMap.speedL, -1 * RobotMap.speedR);
		}
		
		while (!String.valueOf(reachedEndPoint).equals("true")) {
			Robot.drivetrain.tankDrive(-0.4 * RobotMap.speedL, -1 * RobotMap.speedR);
		}
	}

	// returns true when method no longer needs to execute
	protected boolean isFinished() {
		return isTimedOut();
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
