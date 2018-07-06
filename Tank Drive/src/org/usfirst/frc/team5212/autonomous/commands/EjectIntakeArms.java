package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class EjectIntakeArms extends Command {

    public EjectIntakeArms() {
    	requires(Robot.cubeIO);
    	setTimeout(2);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Shoot ARms");
    	Robot.cubeIO.rightFrontArm.set(RobotMap.rightArmOutputSpeed);
    	Robot.cubeIO.leftFrontArm.set(RobotMap.leftArmIntakeSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.cubeIO.rightFrontArm.set(0);
    	Robot.cubeIO.leftFrontArm.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
