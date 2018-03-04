package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ShooterArm extends PIDSubsystem {
	
	public WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.rightShooterArm);
	public WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.leftShooterArm);
	
    public ShooterArm() {
    	super("ShooterArm",
    			RobotMap.armMotorKp,
    			RobotMap.armMotorKi,
    			RobotMap.armMotorKd);
    	setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		left.follow(right); // set to 0 at isfinished
		left.setInverted(true);
    }

    public void initDefaultCommand() {
    }

    public double returnPIDInput() {
		return right.get();
    }

    public void usePIDOutput(double output) {
    	right.set(output);
    }
}
