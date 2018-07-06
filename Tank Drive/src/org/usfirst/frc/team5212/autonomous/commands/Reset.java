package org.usfirst.frc.team5212.autonomous.commands;

import org.usfirst.frc.team5212.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Reset extends Command {

    public Reset() {
    	super("Reset");
    	requires(Robot.pneum);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.pneum.reset();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
//    	Robot.pneum.lock();
    }

    protected void interrupted() {
    	end();
    }
}
