package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;


public class EncoderDrive extends Command {

	Timer autonomousTimer;
	
	public double leftAutonomousInput;
	public double rightAutonomousInput;
	
	
	public EncoderDrive(double driveTime) {
		super("EncoderDrive");
		requires(Robot.drivetrain);
		setTimeout(driveTime);
		
	}
	
	@Override
	protected void initialize() {
//		Robot.drivetrain.leftEncoder.reset();
//		Robot.drivetrain.rightEncoder.reset();
		
//		autonomousTimer = new Timer();
//		autonomousTimer.start();

		leftAutonomousInput = -0.75 * RobotMap.speedL;
		rightAutonomousInput = -0.75 * RobotMap.speedR;
		
	}
//	ratio between joystick values should be the same as the ratio between the two sides
//	limit the check to the joystick being over 30% power
	
	@Override
	protected void execute() {
		/*
		 * incorporate gyroscope driving & turn balancing
		 */
		
//		Robot.drivetrain.encoderDriveStraight(leftAutonomousInput, rightAutonomousInput);
			double[] d = Robot.drivetrain.driveStraight(leftAutonomousInput, rightAutonomousInput);
			Robot.drivetrain.slewTankDrive(d[0], d[1], Robot.panel.getVoltage());
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
