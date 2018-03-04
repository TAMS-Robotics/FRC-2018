package org.usfirst.frc.team5212.robot;

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

		// must init this last because
		// of dependencies and null pointer
		oi = new OI();

	}

	public void autonomousInit() {
		// auton instantiation
		autonomousTimer = new Timer();
		autonomousTimer.start();

		leftAutonomousInput = -0.75 * RobotMap.speedL;
		rightAutonomousInput = -0.75 * RobotMap.speedR;
	}

	public void autonomousPeriodic() {
		// drive straight for 1.97 seconds
		if (autonomousTimer.get() < 1.97) {
			double[] d = drivetrain.driveStraight(leftAutonomousInput, rightAutonomousInput);
			drivetrain.slewTankDrive(d[0], d[1], panel.getVoltage());
		}
		else {
			drivetrain.tankDrive(0, 0);
		}
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		// safety protocol during testing
		// allows operators to disable driving
		if (RobotMap.DRIVE_TRAIN_ENABLED) {
			leftInput = oi.getLeftJoystick();
			rightInput = oi.getRightJoystick();

			if (
					(leftInput > 0 && rightInput > 0) || 
					(leftInput < 0 && rightInput < 0)
				)
			{
				// use balanced joystick input and wheel output
				double[] d = drivetrain.joystickBalance(leftAutonomousInput, rightAutonomousInput);
				drivetrain.slewTankDrive(d[0], d[1], panel.getVoltage());
			}
			else {
				drivetrain.slewTankDrive(leftInput, rightInput, panel.getVoltage());
			}
		}
	}

}