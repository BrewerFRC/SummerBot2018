/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;



public class Robot extends TimedRobot {
	private Xbox driver = new Xbox(0);
	private DriveTrain dt = new DriveTrain();
	
	@Override
	public void robotInit() {
		
		dt.getHeading().calibrate();
		
		super.setPeriod(1/Constants.REFRESH_RATE);
		
	}
	@Override
	public void autonomousInit() {
	
	}

	
	@Override
	public void autonomousPeriodic() {
		
	}

	
	@Override
	public void teleopPeriodic() {
		double power = driver.getY(GenericHID.Hand.kLeft);
		double turn = driver.getX(GenericHID.Hand.kLeft);
		dt.accelDrive(power, turn);
		SmartDashboard.putNumber("Input power: ", power);
	}

	
	@Override
	public void testPeriodic() {
		
		
	}
}
