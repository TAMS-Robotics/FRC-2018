/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5212.robot;

import org.usfirst.frc.team5212.autonomous.commands.PrepareShoot;
import org.usfirst.frc.team5212.autonomous.commands.ReverseOrientation;
//import org.usfirst.frc.team5212.autonomous.commands.ShootAndReset;

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
	
	public OI() {
		a.whenPressed(new ReverseOrientation());
		b.whenPressed(new PrepareShoot());
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
	
	public double getLeftJoystick() {
		return this.j.getRawAxis(RobotMap.leftJoystickPort);
	}
	
	public double getRightJoystick() {
		return this.j.getRawAxis(RobotMap.rightJoystickPort);
	}
	
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
