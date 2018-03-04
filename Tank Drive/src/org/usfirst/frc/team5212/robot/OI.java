/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5212.robot;

import org.usfirst.frc.team5212.autonomous.commands.ArmDown;
import org.usfirst.frc.team5212.autonomous.commands.ArmUp;
import org.usfirst.frc.team5212.autonomous.commands.ClimberDown;
import org.usfirst.frc.team5212.autonomous.commands.ClimberUp;
import org.usfirst.frc.team5212.autonomous.commands.Intake;
import org.usfirst.frc.team5212.autonomous.commands.Reset;
import org.usfirst.frc.team5212.autonomous.commands.ReverseOrientation;
import org.usfirst.frc.team5212.autonomous.commands.Shoot;
import org.usfirst.frc.team5212.autonomous.commands.ShootAndOutput;

//import org.usfirst.frc.team5212.autonomous.commands.Intake;
//import org.usfirst.frc.team5212.autonomous.commands.PrepareShoot;
//import org.usfirst.frc.team5212.autonomous.commands.ReverseOrientation;
//import org.usfirst.frc.team5212.autonomous.commands.ShootAndReset;
//import org.usfirst.frc.team5212.autonomous.commands.ShootAndOutput;
//import org.usfirst.frc.team5212.autonomous.commands.ShootAndReset;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	Joystick j = new Joystick(0);
	
	private Button a = new JoystickButton(j, RobotMap.aButton);
	private Button x = new JoystickButton(j, RobotMap.xButton);
	private Button y = new JoystickButton(j, RobotMap.yButton);
	private Button b = new JoystickButton(j, RobotMap.bButton);
	private Button start = new JoystickButton(j, RobotMap.startButton);
	private Button stop = new JoystickButton(j, RobotMap.stopButton);
	private Button rb = new JoystickButton(j, RobotMap.rbButton);
	private Button rt = new JoystickButton(j, RobotMap.rtButton);
	private Button lb = new JoystickButton(j, RobotMap.lbButton);
	private Button lt = new JoystickButton(j, RobotMap.ltButton);
	
	public OI() {
		start.whenPressed(new ReverseOrientation());
		a.whenPressed(new ClimberDown());
		y.whenPressed(new ClimberUp());
		x.whenPressed(new Reset());
		b.whenPressed(new Shoot());
		rb.whileHeld(new Intake());
		rt.whileHeld(new ShootAndOutput());
		lb.whileHeld(new ArmUp());
		lt.whileHeld(new ArmDown());
	}

	public Button getStart() {
		return start;
	}

	public void setStart(Button start) {
		this.start = start;
	}

	public Button getLb() {
		return lb;
	}

	public void setLb(Button lb) {
		this.lb = lb;
	}

	public Button getLt() {
		return lt;
	}

	public void setLt(Button lt) {
		this.lt = lt;
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
	
	public Button getRb() {
		return rb;
	}

	public void setRb(Button rb) {
		this.rb = rb;
	}

	public Button getRt() {
		return rt;
	}

	public void setRt(Button rt) {
		this.rt = rt;
	}
	
	public double getLeftJoystick() {
		return this.j.getRawAxis(RobotMap.leftJoystickPort);
	}
	
	public double getRightJoystick() {
		return this.j.getRawAxis(RobotMap.rightJoystickPort);
	}
}
