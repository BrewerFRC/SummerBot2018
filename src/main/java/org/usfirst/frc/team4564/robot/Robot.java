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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class Robot extends TimedRobot {
	private Xbox driver = new Xbox(0);
	private DriveTrain dt = new DriveTrain();
	private PID pidarmobj;
	private PID pidheadingobj;
	private ADXRS450_Gyro gyro;
	private double target;
	
	private Proximity proximitysensor;
	@Override
	public void robotInit() {
		super.setPeriod(1/Constants.REFRESH_RATE);
		
		pidarmobj = new PID(Constants.ARMP, Constants.ARMI, Constants.ARMD, false, false, "Arm position");
		pidheadingobj = new PID(Constants.HEADINGP, Constants.HEADINGI, Constants.HEADINGD, false, false, "Heading");
		
		gyro = new ADXRS450_Gyro();
		gyro.calibrate();
		target = 0;

		proximitysensor = new Proximity(Constants.IR_PORT);

		pidheadingobj.setOutputLimits(-DriveTrain.TURNMAX, DriveTrain.TURNMAX);
	}
	@Override
	public void autonomousInit() {
	
	}

	
	@Override
	public void autonomousPeriodic() {
		
	}
	@Override
	public void teleopInit() {
		gyro.reset();
	}
	
	@Override
	public void teleopPeriodic() {
		double power = driver.getY(GenericHID.Hand.kLeft);
		double turn = driver.getX(GenericHID.Hand.kLeft);
		
		SmartDashboard.putNumber("Input power", power);
		
		//double turn;
		/*double headingdeg = gyro.getAngle();
		SmartDashboard.putNumber("Angle", headingdeg);
		if(driver.getPressed("a")){
			turn = pidheadingobj.calc(headingdeg);
			
			
			if(driver.when("dPadRight")){
				target+= 20;
				System.out.println("TestBot zeig heil!");

			}
			if(driver.when("dPadLeft")){
				target-= 20;
			}
		}
		else{
			turn = 0;
		}
		*/
		//pidheadingobj.setTarget(target);

		double infaredRaw = proximitysensor.CheckInfaredSensor();
		double infaredDist = proximitysensor.ToDist((float)infaredRaw);
		
		SmartDashboard.putNumber("True Infared Dist", infaredDist);
		dt.arcadeDrive(power, turn);
		pidheadingobj.update();
	}

	
	@Override
	public void testPeriodic() {
		if(driver.when("a")){
			gyro.calibrate();
		}
		
	}
}