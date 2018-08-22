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
	
	
	
	/** 
	 * Solenoid stuff
	 */
	PCM_CAN_ID = 1, //The id of the solenoid board
	SHIFTER = 1 //The channel of the shifter on the solenoid board
	
	
	;
	public static final double 
	
	MAX_ACCEL = 0.5,
	
	ARMP = 1,
	ARMI = 1,
	ARMD = 1,

	HEADINGP = DriveTrain.TURNMAX/10,
	HEADINGI = 0,
	HEADINGD = 0
	;
}