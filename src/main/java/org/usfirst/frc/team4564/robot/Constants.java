package org.usfirst.frc.team4564.robot;

public class Constants {
	public static final int

	REFRESH_RATE = 5,

			// PWM
			DRIVE_FL = 0, // 0
			DRIVE_FR = 2, // 2
			DRIVE_BL = 1, // 1
			DRIVE_BR = 3, // 3

			INTAKE_1 = 5,
			// DIO
			DRIVE_ENCODER_LA = 1, DRIVE_ENCODER_RA = 2, DRIVE_ENCODER_LB = 3, DRIVE_ENCODER_RB = 4,

			ARM_M = 4,
			// Analog Input
			IR_PORT = 1, ARM_POT = 0,

			ARM_POT_SCALAR = 1, ARM_MIN_DEG = 0, ARM_MAX_DEG = 180

	;
	public static final double

	MAX_ACCEL = 0.5,

			MIN_ARM_MAGNITUDE = 0.45, ARMP = 1, ARMI = 0, ARMD = 0,

			ARMMAXACCEL = 0.7f, MAX_ARM_VELOCITY = 5, MIN_ARM_VELOCITY = 40, MIN_ARM_ACC = 0.45,

			HEADINGP = DriveTrain.TURNMAX / 10, HEADINGI = 0, HEADINGD = 0

	;
}
