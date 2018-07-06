package org.usfirst.frc.team5212.robot;

import org.usfirst.frc.team5212.autonomous.commands.EjectIntakeArms;
import org.usfirst.frc.team5212.autonomous.commands.EncoderDrive;
import org.usfirst.frc.team5212.autonomous.subsystems.CubeIO;
import org.usfirst.frc.team5212.autonomous.subsystems.DriveTrain;
import org.usfirst.frc.team5212.autonomous.subsystems.Pneumatics;
import org.usfirst.frc.team5212.autonomous.subsystems.ShooterArm;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends IterativeRobot {
	// classwide objects
	public static Timer autonomousTimer;
	public static PowerDistributionPanel panel;
	public static OI oi;

	// subsystem creations
	public static DriveTrain drivetrain;
	public static Pneumatics pneum;
	public static CubeIO cubeIO; 
	public static ShooterArm arm;

	NetworkTableInstance inst;
	NetworkTable visionTable;

	NetworkTableEntry leftRawData;
	NetworkTableEntry rightRawData;
	NetworkTableEntry testNT;

	double leftInput;
	double rightInput;
	double leftAutonomousInput;
	double rightAutonomousInput;
	
	Command encoderDrive;
	Command armOut;

	public void robotInit() {
		
		// network table init
		inst = NetworkTableInstance.getDefault();
		visionTable = inst.getTable("vision");
		testNT = visionTable.getEntry("test");

		if (testNT.getDouble(0.0) == 123.0) {
			System.out.println("NetworkTables is working");
		}
		else {
			System.out.println("ERROR: NetworkTables is not working");
		}
		
		// machine init
		panel = new PowerDistributionPanel(RobotMap.pdpPort);

		// subsystem init
		drivetrain = new DriveTrain();
		pneum = new Pneumatics();
		cubeIO = new CubeIO();
		arm = new ShooterArm();

		CameraServer.getInstance().startAutomaticCapture();

		// notify which systems are manually disabled
		if(!RobotMap.CLIMBER_ENABLED)
			System.out.println("WARNING: Climber is DISABLED");
		if(!RobotMap.DRIVE_TRAIN_ENABLED)
			System.out.println("WARNING: Drive train is DISABLED");
		if(!RobotMap.SHOOTER_ENABLED)
			System.out.println("WARNING: Shooter is DISABLED");
		
		// must init this last because
		// of dependencies and null pointer
		oi = new OI();
	}

	public void autonomousInit() {
		// auton instantiation with drive time
		autonomousTimer = new Timer();
		autonomousTimer.start();
		leftAutonomousInput = -0.75 * 1;
		rightAutonomousInput = -0.75 * 1;
		
		arm.realign();
//		armOut = new EjectIntakeArms();	
	}

	public void autonomousPeriodic() {
		// drive straight for 1.97 seconds
		// encoderDrive.start();
		if (autonomousTimer.get() < 1.97) {
//			double[] d = drivetrain.autonomousJoystickBalance(-.75, -.75);
//			System.out.println("d0: " + d[0] + " d1:" + d[1]);
			Robot.drivetrain.slewTankDrive(leftAutonomousInput, rightAutonomousInput, Robot.panel.getVoltage());

//			drivetrain.slewTankDrive(d[0], d[1], panel.getVoltage());
//	    	Robot.cubeIO.rightFrontArm.set(RobotMap.rightArmOutputSpeed);
//	    	Robot.cubeIO.leftFrontArm.set(RobotMap.leftArmIntakeSpeed);
		}
//		else if (autonomousTimer.get() > 2 && autonomousTimer.get() < 2.31) {
//			drivetrain.slewTankDrive(-0.6, 0.6, panel.getVoltage());
//		}
		else {
			Robot.drivetrain.tankDrive(0, 0);
//	    	Robot.cubeIO.r-ightFrontArm.set(0);
//	    	Robot.cubeIO.leftFrontArm.set(0);

		}
		
//		armOut.start();
	}

	public void teleopInit() {
		arm.realign();
	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
//		System.out.println("Target: " + arm.right.getClosedLoopTarget(0));
//		System.out.println("Current pos: " + arm.right.getSelectedSensorPosition(0));
//		System.out.println("initpos: " + arm.initPosition);
		System.out.println("Right encoder distance: " + drivetrain.rightEncoder.getDistance());
		System.out.println("Right encoder speed: " + drivetrain.rightEncoder.getRate());
//		System.out.println("Right encoder dpp: " + drivetrain.rightEncoder.getDistancePerPulse());
//		System.out.println("Right encoder get: " + drivetrain.rightEncoder.get());
		System.out.println("Left encoder distance: " + drivetrain.leftEncoder.getDistance());		
		System.out.println("Left encoder speed: " + drivetrain.leftEncoder.getRate());
//		System.out.println(c "Left encoder dpp: " + drivetrain.leftEncoder.getDistancePerPulse());
//		System.out.println("Left encoder get: " + drivetrain.leftEncoder.get());
		// safety protocol during testing
		// allows operators to disable driving
		if (RobotMap.DRIVE_TRAIN_ENABLED) {
			leftInput = oi.getLeftJoystick();
			rightInput = oi.getRightJoystick();

			if (
					(leftInput > 0 && rightInput < 0) || 
					(leftInput < 0 && rightInput > 0)
				)
			{
				
				// use balanced joystick input and wheel output
				drivetrain.slewTankDrive(leftInput * .65, rightInput * .65, panel.getVoltage());
			}
			else {
//				double[] d = drivetrain.joystickBalance(leftInput, rightInput);
//				leftInput = d[0];
//				rightInput = d[1];

				drivetrain.slewTankDrive(leftInput, rightInput, panel.getVoltage());
			}
		}
		
	}
	
	public void disabledPeriodic() {
//		System.out.println("Current pos: " + arm.right.getSelectedSensorPosition(0));
	}

}