package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Auto{
    DriveTrain dt;
    ADXRS450_Gyro gyro;
    Arm arm;
    /*
    public static final int START_CENTER = 0;
    public static final int START_LEFT = 1;
    public static final int START_RIGHT = 2;
    */
    public static final int INIT = 0;
    public static final int BEGIN_TURN_LEFT = 1;
    public static final int BEGIN_TURN_RIGHT = 2;
    public static final int SWITCH_ALIGN_LEFT = 3;
    public static final int SWITCH_ALIGN_RIGHT = 4;
    public static final int RAM_SWITCH = 6;
    
    public Auto(DriveTrain dt, ADXRS450_Gyro gyro, Arm arm){
        this.dt = dt;
        this.gyro = gyro;
        this.arm = arm;

        
        
    }

    public void init(){
        
    }

    public void driveSwitch(int switchPos){
        switch(switchPos){
            case INIT:

            case BEGIN_TURN_LEFT:

            case SWITCH_ALIGN_LEFT:


        }
    }
}