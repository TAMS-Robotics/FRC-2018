package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ArmDown extends Command {

	public ArmDown() {
		requires(Robot.arm);
		setTimeout(14);
	}
	
	@Override
	protected void initialize() {

	}
	
	@Override
	protected void execute() {
//		Robot.arm.right.setSelectedSensorPosition(0, 0, 1000);
//		double rightPosition = Robot.arm.right.getSelectedSensorPosition(0);
//		System.out.println("Right pos: " + rightPosition);
//		Robot.arm.usePIDOutput(5);
		
		double rightArmValue = Robot.arm.right.get();
		System.out.println(rightArmValue); // debugging
		
		if(rightArmValue < 321) {
			Robot.arm.right.set(-.5);
			Robot.arm.left.set(-.5);
		}	 

	}
	
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}
	
	@Override
	protected void end() {
		Robot.arm.right.stopMotor();
		Robot.arm.left.stopMotor();
	}
	
	@Override
	protected void interrupted() { 
		end();
	}
}
