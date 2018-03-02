package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

public class EncoderTurn extends Command {
	enum Turn {
		RIGHT,
		LEFT;
	}
	
	double leftEncoderDistance;
	double rightEncoderDistance;
	
	Turn turn = null;
	
	public EncoderTurn() {
		requires(Robot.drivetrain);
		setTimeout(5);
	}
	
	protected void initialize() {
		// TODO Auto-generated method stub
	}
	
	protected void execute() {
		double arcDistance = 12.6 * Math.PI;
		boolean condition = false; // get this data from vision - turn right or left
		
		if (String.valueOf(condition).equalsIgnoreCase("true")) {
			Robot.drivetrain.tankDrive(-0.5 * RobotMap.speedL, 0.5 * RobotMap.speedR);
		} 
		else {
			Robot.drivetrain.tankDrive(0.5 * RobotMap.speedL, -0.5 * RobotMap.speedR);
		}

		// d = (pi/2) * r; where theta = pi/2
		// r = 25.2 in
		// p = 50.0% 
		// drive forward d feet at p power
		
		// TODO: implement multidirectional turning
		// 		test the code already written
		// 		determine whether distance swapping is correct
		// 		slew? y/n
		// 		any thing else goes h e r e 
		while (leftEncoderDistance != (arcDistance)) {
			Robot.drivetrain.tankDrive(-0.5 * RobotMap.speedL, 0.50 * RobotMap.speedR);
		}
		// TODO Auto-generated method stub
	}

	protected boolean isFinished() {
		return isTimedOut();
	}

	protected void end() {
	
	}
		
	protected void interrupted() {
		end();
	}
}
