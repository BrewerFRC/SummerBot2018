package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Auto{
    DriveTrain dt;
    ADXRS450_Gyro gyro;
    Arm arm;
    /*
    public static final int START_CENTER = 0;
    public static final int START_LEFT = 1;
    public static final int START_RIGHT = 2;
    */
    private static final int IDLE = 0;
    private static final int INIT = 1; //Read time; parameters; start drive.
    private static final int FIRST_TURN = 2; //Monitor drive time; start second turn.
    private static final int SECOND_TURN = 3; //Monitor drive time; stop drive.
    private static final int RAM_SWITCH = 4;

    private static final int LAUNCH_LEFT = 5;
    private static final int LAUNCH_RIGHT = 6;
    
    public static final double INITIAL_TURN = 0.37;
    public static final double INITIAL_SPEED = -0.73;

    double speed;
    double turn;

    int state = IDLE;
    
    public boolean isTesting = false;

    public long timer;
    public Auto(DriveTrain dt, ADXRS450_Gyro gyro, Arm arm){
        this.dt = dt;
        this.gyro = gyro;
        this.arm = arm;

        this.speed = 0;
        this.turn = 0;
        init();
    }

    public void init(){
        SmartDashboard.putNumber("Turn", INITIAL_TURN);
        SmartDashboard.putNumber("Speed", INITIAL_SPEED);
    }

    public void update(){
        SmartDashboard.putNumber("State", state);
        driveSwitch(LAUNCH_RIGHT);


    }
    public void start(){
        if(state == IDLE){
            state = INIT;
        }
    }
    private void driveSwitch(int switchPos){
        if(switchPos == LAUNCH_LEFT){
            
        
        }
        else if (switchPos == LAUNCH_RIGHT){
            switch(state){
                case IDLE:
                    dt.arcadeDrive(0, 0);
                    break;
                case INIT:
                    timer = Common.time();

                    speed = SmartDashboard.getNumber("Speed",INITIAL_SPEED);
                    turn = SmartDashboard.getNumber("Turn", INITIAL_TURN);

                    state = FIRST_TURN;
                    break;
                case FIRST_TURN:
                    dt.arcadeDrive(speed, turn);
                    if(Common.time() - timer >= 1500){
                        timer = Common.time();
                        state = SECOND_TURN;
                    }
                    break;
                case SECOND_TURN:
                    dt.arcadeDrive(speed, -turn);
                    if(Common.time() - timer >= 1800){                        timer = Common.time();
                        state = IDLE;
                    }
                    break;
            }
        }
    }
}