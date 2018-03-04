package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


public class EncoderDrive extends Command {
	
	public EncoderDrive() {
		super("EncoderDrive");
		requires(Robot.drivetrain);
		setTimeout(5);
	}
	
	@Override
	protected void initialize() {
		Robot.drivetrain.leftEncoder.reset();
		Robot.drivetrain.rightEncoder.reset();
	}
//	ratio between joystick values should be the same as the ratio between the two sides
//	limit the check to the joystick being over 30% power
	
	@Override
	protected void execute() {

	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	@Override 
	protected void end() {
		Robot.drivetrain.tankDrive(0, 0);
	}
	
	@Override
	protected void interrupted() {
		this.end();
	}
}
