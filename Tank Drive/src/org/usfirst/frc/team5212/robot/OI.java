/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5212.robot;

import org.usfirst.frc.team5212.autonomous.commands.Intake;
import org.usfirst.frc.team5212.autonomous.commands.PrepareShoot;
import org.usfirst.frc.team5212.autonomous.commands.ReverseOrientation;
//import org.usfirst.frc.team5212.autonomous.commands.ShootAndReset;
import org.usfirst.frc.team5212.autonomous.commands.ShootAndOutput;
import org.usfirst.frc.team5212.autonomous.commands.ShootAndReset;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
//	public static Joystick j = new Joystick(0);
//	public static Button a = new JoystickButton(j, RobotMap.aButton);
//	public static Button b = new JoystickButton(j, RobotMap.bButton);
//	public static Button x = new JoystickButton(j, RobotMap.xButton);
//	public static Button y = new JoystickButton(j, RobotMap.yButton);
	
	Joystick j = new Joystick(0);
	Button a = new JoystickButton(j, RobotMap.aButton);
	Button b = new JoystickButton(j, RobotMap.bButton);
	Button x = new JoystickButton(j, RobotMap.xButton);
	Button y = new JoystickButton(j, RobotMap.yButton);
	
	Button in, out; // responsible for intake and outtake, respectively
	Button shoot;
	
	public OI() {
		a.whenPressed(new ReverseOrientation());
		b.whenPressed(new PrepareShoot());
		in.whenPressed(new Intake());
		out.whenPressed(new ShootAndOutput());
		shoot.whenPressed(new ShootAndReset());
	}

	public Joystick getJ() {
		return j;
	}

	public void setJ(Joystick j) {
		this.j = j;
	}

	public Button getA() {
		return a;
	}

	public void setA(Button a) {
		this.a = a;
	}

	public Button getB() {
		return b;
	}

	public void setB(Button b) {
		this.b = b;
	}

	public Button getX() {
		return x;
	}

	public void setX(Button x) {
		this.x = x;
	}

	public Button getY() {
		return y;
	}

	public void setY(Button y) {
		this.y = y;
	}
	
	public Button getIn() {
		return in;
	}
	
	public void setIn(Button in) {
		this.in = in;
	}
	
	public Button getOut() {
		return out;
	}
	
	public void setOut(Button out) {
		this.out = out;
	}
	
	
	
	public double getLeftJoystick() {
		return this.j.getRawAxis(RobotMap.leftJoystickPort);
	}
	
	public double getRightJoystick() {
		return this.j.getRawAxis(RobotMap.rightJoystickPort);
	}
}
