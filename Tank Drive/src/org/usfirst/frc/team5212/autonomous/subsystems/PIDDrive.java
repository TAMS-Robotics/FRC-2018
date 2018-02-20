package org.usfirst.frc.team5212.autonomous.subsystems;
import org.usfirst.frc.team5212.robot.Robot;
import org.usfirst.frc.team5212.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;


public class PIDDrive extends PIDSubsystem { // This system extends PIDSubsystem
	public Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
	public Encoder rightEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);



//	leftEncoder.setMaxPeriod(.1);
//	leftEncoder.setMinRate(10);
//	leftEncoder.setReverseDirection(true);
//	leftEncoder.setSamplesToAverage(7);

	public PIDDrive() {
		super("PIDDrive", RobotMap.frontLeftMotorKp, RobotMap.frontLeftMotorKi, RobotMap.frontLeftMotorKd);
		// The constructor passes a name for the subsystem and the P, I and D constants that are used when computing the motor output
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
	}
	
    public void initDefaultCommand() {
    }
    
    
    protected double returnPIDInput() {
    	return leftEncoder.getDistance()-rightEncoder.getDistance(); // returns the sensor value that is providing the feedback for the system
    }

    protected void usePIDOutput(double output) {
    	Robot.drivetrain.frontLeftMotor.pidWrite(output);
    	Robot.drivetrain.frontRightMotor.pidWrite(output); // this is where the computed output value fromthe PIDController is applied to the motor
    }
}