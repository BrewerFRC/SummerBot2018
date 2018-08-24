package org.usfirst.frc.team4564.robot;

public class Constants {
	public static final int
	
	REFRESH_RATE = 50,
	
	//Ports
	DRIVE_FL = 6,
	DRIVE_FR = 8,
	DRIVE_BL = 7,
	DRIVE_BR = 9,
	
	DRIVE_ENCODER_LA = 1,
	DRIVE_ENCODER_RA = 2,
	DRIVE_ENCODER_LB = 3,
	DRIVE_ENCODER_RB = 4,
	IR_PORT = 3,
	
	ARM_M = 10,
	ARM_POT = 9,

	ARM_POT_SCALAR = 0,
	ARM_MIN_DEG = 0,
	ARM_MAX_DEG = 180,

	ARM_MIN_ACC, ARM_MAX_ACC = 
	
	/** 
	 * Solenoid stuff
	 */
	PCM_CAN_ID = 1, //The id of the solenoid board
	SHIFTER = 1 //The channel of the shifter on the solenoid board
	
	
	;
	public static final double 
	
	MAX_ACCEL = 0.5,
	
	ARMP = 1,
	ARMI = 0,
	ARMD = 0,

	HEADINGP = DriveTrain.TURNMAX/10,
	HEADINGI = 0,
	HEADINGD = 0

	;
}
