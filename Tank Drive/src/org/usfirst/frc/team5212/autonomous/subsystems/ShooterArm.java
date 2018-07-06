package org.usfirst.frc.team5212.autonomous.subsystems;

import org.usfirst.frc.team5212.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class ShooterArm extends PIDSubsystem {
	
	public WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.leftShooterArm);
	public WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.rightShooterArm);
	
	public int initPosition;
	public int bufferInitPosition;
	public int absoluteScalePosition;
	public int absoluteSwitchPosition;
	public int absoluteRaisedPosition;
	
	/*public enum Position {
		INIT,
		SWITCH,
		SCALE
	}*/
	
	public enum Position{
		INIT,
		RAISED
	}
	
	public Position currentPosition;
	
    public ShooterArm() {
    	super("ShooterArm",
    			RobotMap.armMotorKp,
    			RobotMap.armMotorKi,
    			RobotMap.armMotorKd);
    	
    	right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    	right.setSensorPhase(false);
    	right.setInverted(false);
    	right.configAllowableClosedloopError(0, 10, 10);
    	
//    	right.configNominalOutputForward(0, 10);
//    	right.configNominalOutputReverse(0, 10);
    	right.configPeakOutputForward(.5, 10);
    	right.configPeakOutputReverse(-.5, 10);
    	    	
    	right.config_kF(0, RobotMap.feedForward, 10);
    	right.config_kP(0, RobotMap.armMotorKp, 10);
    	right.config_kI(0, RobotMap.armMotorKi, 10);
    	right.config_kD(0, RobotMap.armMotorKd, 10);
    	
    //	right.setSelectedSensorPosition(initPosition, 0, 10);
    	
//    	realign();
    	
 //   	setAbsoluteTolerance(0.05);
//		getPIDController().setContinuous(false);
		left.follow(right); // set to 0 at isfinished
//		left.setInverted(true);
    }
    
    public void realign() {
    	initPosition = right.getSelectedSensorPosition(0);
    	bufferInitPosition = initPosition + 100;
//    	absoluteScalePosition = initPosition + RobotMap.relativeFloorToScalePostion;
//    	absoluteSwitchPosition = initPosition + RobotMap.relativeFloorToSwitchPostion;
    	absoluteRaisedPosition = initPosition + RobotMap.raisedPosition;
    	right.set(ControlMode.Position, initPosition);
    	
    	currentPosition = Position.INIT;
    }

    public void initDefaultCommand() {
    }

    public double returnPIDInput() {
//		return right.get();
    	return right.getSelectedSensorPosition(0);
    	
    }

    public void usePIDOutput(double output) {
    	right.set(output);
    }
    
    public void stopMotors() {
    	System.out.println("stop motors");
    	right.stopMotor();
    	left.stopMotor();
    }
    
}
