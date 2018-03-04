package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ArmUp extends Command {

	public ArmUp() {
		requires(Robot.arm);
		setTimeout(14);
	}
	
	@Override
	protected void initialize() {
		
	}
	
	@Override
	protected void execute() {
		double a = Robot.arm.right.getSelectedSensorPosition(0);
		double rightArmValue = Robot.arm.right.get();
//		double rightPosition = Robot.arm.right.getSelectedSensorPosition(0);
//		System.out.println("Right pos: " + rightPosition);
		System.out.println(rightArmValue);
		
		if(rightArmValue < 321) {
			Robot.arm.right.set(1);
			Robot.arm.left.set(1);
		}
		
		System.out.println("Encoder ticks: " + a);
//		Robot.arm.setSetpoint(10);
//		Robot.arm.right.setSelectedSensorPosition(10, 0, 2000);
//		Robot.arm.usePIDOutput(321);
		
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
