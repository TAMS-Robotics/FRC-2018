package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.autonomous.subsystems.ShooterArm;
import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Yeet extends Command {

    public Yeet() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.arm);
    	setTimeout(5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (
    			(Robot.arm.currentPosition == ShooterArm.Position.INIT) &&
    			(Robot.arm.right.getSelectedSensorPosition(0) < RobotMap.armHeightMax)
    	)
    	{
    		Robot.arm.right.set(.6); // yeet
		}
    	else if (Robot.arm.currentPosition != ShooterArm.Position.INIT) {
    		Robot.arm.right.set(ControlMode.Disabled, Robot.arm.bufferInitPosition);
			try {
				Thread.sleep(850);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Robot.arm.right.set(ControlMode.Position, Robot.arm.initPosition);
			
			Robot.arm.currentPosition = ShooterArm.Position.INIT;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.arm.right.set(ControlMode.Disabled, Robot.arm.bufferInitPosition);
    	Robot.arm.right.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
