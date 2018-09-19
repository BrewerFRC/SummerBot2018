/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
	private Xbox driver = new Xbox(0);
	private DriveTrain dt = new DriveTrain();
	private ADXRS450_Gyro gyro;
	private Arm arm;
	private Auto auto = new Auto(dt, gyro, arm);
	private Intake intake;

	private Proximity proximitysensor;

	@Override
	public void robotInit() {
		// super.setPeriod(1/Constants.REFRESH_RATE);
		intake = new Intake();
	}

	@Override
	public void autonomousInit() {
		auto.start();
		intake.reset();
	}

	@Override
	public void autonomousPeriodic() {
		auto.update();
		intake.update();
	}

	@Override
	public void teleopInit() {
		intake.reset();
	}

	@Override
	public void teleopPeriodic() {
		if (driver.when("a") == true) {
			Interrupts.setAButton(true);
		}
		if (driver.when("rightTrigger") == true) {
			Interrupts.setRT(true);
		}

		Common.dashNum("IR READING", intake.getCubeDistance());
		Common.dashBool("Is fully loaded", intake.isFullyLoaded());
		Common.dashBool("Is partially loaded", intake.isPartiallyLoaded());

		/*
		 * double forward = 0; double turn = 0; forward =
		 * driver.getY(GenericHID.Hand.kLeft); turn =
		 * driver.getX(GenericHID.Hand.kLeft);
		 * 
		 * dt.accelDrive(forward, -turn);
		 */
		intake.update();
		/*
		 * if(driver.getPressed("a")){ auto.start(); } auto.update();
		 * 
		 * double power = driver.getY(GenericHID.Hand.kLeft); double turn =
		 * driver.getX(GenericHID.Hand.kLeft);
		 * 
		 * SmartDashboard.putNumber("Input power", power);
		 * 
		 * 
		 * double infaredRaw = proximitysensor.CheckInfaredSensor(); double infaredDist
		 * = proximitysensor.ToDist((float)infaredRaw);
		 * 
		 * SmartDashboard.putNumber("True Infared Dist", infaredDist);
		 * dt.arcadeDrive(power, turn);
		 */
	}

	@Override
	public void testPeriodic() {
		if (driver.when("a")) {
			gyro.calibrate();
		}

	}
}
