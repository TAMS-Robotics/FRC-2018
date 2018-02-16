package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.autonomous.subsystems.DriveBase;
import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class EncoderDrive extends Command {
	Timer t;
	double speedL;
	double speedR;
	
	public EncoderDrive() {
		super("EncoderDrive");
//		 requires(Robot.driveBase);
		t = new Timer();
	}
	
	// called once when this Command is initialized
	protected void initialize() {
		speedL = 1;
		speedR = 1;
		t.start();
	}
	
	// called repeatedly in Autonomous mode
	protected void execute() {
		while (t.get() <= RobotMap.TIMEOUT) {
			//base.forward();
		}
		// TODO Auto-generated method stub
		
		
	}

	// returns true when method no longer needs to execute
	protected boolean isFinished() {
		return (t.get() > RobotMap.TIMEOUT);
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
