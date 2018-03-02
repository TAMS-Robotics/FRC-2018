package org.usfirst.frc.team5212.autonomous.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class ShootAndReset extends CommandGroup {

    public ShootAndReset() {
    	System.out.println("Running ShootAndReset");
    	addSequential(new PrepareShoot());
    	addSequential(new Shoot());
    	addSequential(new PrepareReset());
    	addSequential(new Reset());
    }
}
