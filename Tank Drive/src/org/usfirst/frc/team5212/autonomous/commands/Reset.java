package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Reset extends Command {

    public Reset() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	super("Reset");
    	requires(Robot.pneum);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.pneum.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.pneum.lock();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
