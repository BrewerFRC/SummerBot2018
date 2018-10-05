/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

public class Robot extends TimedRobot {
	private Xbox driver = new Xbox(0);
	private DriveTrain dt = new DriveTrain();
	private ADXRS450_Gyro gyro;
	private Arm arm;
	private Intake intake;
	private Auto auto;
	private Compressor compressor;

	private Proximity proximitysensor;
	private String gameData;

	@Override
	public void robotInit() {
		// super.setPeriod(1/Constants.REFRESH_RATE);
		intake = new Intake();
		arm = new Arm();
		compressor = new Compressor();
		auto = new Auto(dt, gyro, arm, intake);
		// SmartDashboard.putString("Auto type", "C");

		SmartDashboard.putNumber("Auto type num", 0);

	}

	@Override
	public void autonomousInit() {
		auto.start();
		intake.reset();
	}

	@Override
	public void disabledPeriodic() {
		// super.disabledPeriodic();
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		// Common.debug("Running periodic disabled");
		if (gameData != null) {
			Common.dashStr("Game Data", gameData);
			if (gameData.length() == 3) {
				auto.setGameData(gameData.toUpperCase());
			}
		}

		if (SmartDashboard.getNumber("Auto type num", 0) == 0) {
			auto.setGameData("CCC");
			// Common.debug("Cross auto type");
		}
		// Common.debug(SmartDashboard.getString("Auto type", "Something isn't
		// working"));
		// Common.debug((String)SmartDashboard.getNumber("Auto type num", 0));
		// auto.setGameData("CCC");
	}

	@Override
	public void autonomousPeriodic() {
		auto.update();
		// arm.ArmUpdate();
		// intake.update();
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
		if (driver.when("b") == true) {
			Interrupts.setBButton(true);
		}
		if (driver.when("leftTrigger") == true) {
			Interrupts.setHThrow(true);
		}

		if (driver.when("dPadUp")) {
			dt.shiftUp();
		}

		if (driver.when("dPadDown")) {
			dt.shiftDown() ;
		}


		compressor.setClosedLoopControl(true);

		Common.dashNum("IR READING", intake.getCubeDistance());
		Common.dashBool("Is fully loaded", intake.isFullyLoaded());
		Common.dashBool("Is partially loaded", intake.isPartiallyLoaded());

		double forward = 0;
		double turn = 0;
		forward = driver.getY(GenericHID.Hand.kLeft);
		turn = driver.getX(GenericHID.Hand.kLeft);

		dt.accelDrive(-forward, -turn);

		intake.update();
		// arm.ArmUpdate();

		arm.powerArm(driver.getY(GenericHID.Hand.kRight));
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
		compressor.setClosedLoopControl(true);
	}
}
