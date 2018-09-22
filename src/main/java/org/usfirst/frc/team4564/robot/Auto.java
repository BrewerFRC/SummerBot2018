package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
    Auto speed 0.7
    Auto turn 0.37
*/
public class Auto {
    DriveTrain dt;
    ADXRS450_Gyro gyro;
    Arm arm;
    Intake intake;
    String gameData;
    /*
     * public static final int START_CENTER = 0; public static final int START_LEFT
     * = 1; public static final int START_RIGHT = 2;
     */
    private static final int IDLE = 0;
    private static final int INIT = 1; // Read time; parameters; start drive.
    private static final int FIRST_TURN = 2; // Monitor drive time; start second turn.
    private static final int SECOND_TURN = 3; // Monitor drive time; stop drive.
    private static final int RAM_SWITCH = 4; // Gently ram switch
    private static final int ARM_DOWN = 5; // Arm down to 45 deg
    private static final int THROW = 6; // Eject cube

    private static final int LAUNCH_LEFT = 10;
    private static final int LAUNCH_RIGHT = 11;

    public static final double INITIAL_TURN = 0.32;
    public static final double INITIAL_SPEED = 0.65;

    double speed;
    double turn;
    double time1st, time2nd;

    int state = IDLE;

    public boolean isTesting = false;

    public long timer;

    public Auto(DriveTrain dt, ADXRS450_Gyro gyro, Arm arm, Intake intake) {
        this.dt = dt;
        this.gyro = gyro;
        this.arm = arm;
        this.intake = intake;
        this.speed = 0;
        this.turn = 0;
        init();
    }

    public void autoDrive(double speed, double turn) {
        this.dt.arcadeDrive(speed, turn);
    }

    public void init() {
        SmartDashboard.putNumber("Turn", INITIAL_TURN);
        SmartDashboard.putNumber("Speed", INITIAL_SPEED);
        SmartDashboard.putNumber("Time 1st turn", 1000);
        SmartDashboard.putNumber("Time 2nd turn", 1200);

        this.intake.armClosed();
    }

    public void setGameData(String gameData) {
        this.gameData = gameData.substring(0, 3);
    }

    public void update() {
        SmartDashboard.putNumber("State", state);
        if (this.gameData.charAt(0) == 'L') {
            driveSwitch(LAUNCH_LEFT);
            Common.debug("Left auto");
        } else if (this.gameData.charAt(0) == 'R') {
            driveSwitch(LAUNCH_RIGHT);
            Common.debug("Right auto");
        } else {
            driveSwitch(9000);
            Common.debug("Starting cross line auto");
        }
    }

    public void start() {
        if (state == IDLE) {
            state = INIT;
        }
    }

    private void driveSwitch(int switchPos) {
        if (switchPos == LAUNCH_LEFT) {
            switch (state) {
            case IDLE:
                Common.debug("Left auto init");
                autoDrive(0, 0);
                break;
            case INIT:
                timer = Common.time();
                speed = SmartDashboard.getNumber("Speed", INITIAL_SPEED);
                speed = 0.7; // was 0.65
                turn = SmartDashboard.getNumber("Turn", INITIAL_TURN);
                turn = -0.47;
                time1st = SmartDashboard.getNumber("Time 1st turn", 1200);
                time1st = 1200;
                time2nd = SmartDashboard.getNumber("Time 2nd turn", 1400);
                time2nd = 1800;
                state = FIRST_TURN;
                break;
            case FIRST_TURN:
                autoDrive(speed, turn);
                if (Common.time() - timer >= time1st) {
                    timer = Common.time();
                    state = SECOND_TURN;
                }
                break;
            case SECOND_TURN:
                autoDrive(speed, -turn);
                if (Common.time() - timer >= time2nd) {
                    timer = Common.time();
                    state = RAM_SWITCH;
                }
                break;
            case RAM_SWITCH:
                autoDrive(0.6, 0);
                if (Common.time() - timer >= 1500) {
                    timer = Common.time();
                    state = ARM_DOWN;
                }
                break;
            case ARM_DOWN:
                this.arm.armSafety(0.3);
                autoDrive(0, 0);
                Common.debug("Moving arm down");
                if (this.arm.GetDegree() >= 45) {
                    this.arm.armSafety(0);
                    timer = Common.time();
                    state = THROW;
                }
                break;
            case THROW:
                this.intake.setMotor(-0.65);
                autoDrive(0, 0);
                Common.debug("Throwing");
                if (Common.time() - timer >= 1000) {
                    this.intake.setMotor(0);
                    state = IDLE;
                }
                break;

            }
        } else if (switchPos == LAUNCH_RIGHT) {
            switch (state) {
            case IDLE:
                autoDrive(0, 0);
                Common.debug("Left auto init");
                break;
            case INIT:
                timer = Common.time();
                speed = SmartDashboard.getNumber("Speed", INITIAL_SPEED);
                speed = 0.65;
                turn = SmartDashboard.getNumber("Turn", INITIAL_TURN);
                turn = 0.45;
                time1st = SmartDashboard.getNumber("Time 1st turn", 1200);
                time1st = 1400; // was 1200
                time2nd = SmartDashboard.getNumber("Time 2nd turn", 1400);
                time2nd = 1600; // was 1400
                state = FIRST_TURN;
                break;
            case FIRST_TURN:
                autoDrive(speed, turn);
                if (Common.time() - timer >= time1st) {
                    timer = Common.time();
                    state = SECOND_TURN;
                }
                break;
            case SECOND_TURN:
                autoDrive(speed, -turn);
                if (Common.time() - timer >= time2nd) {
                    timer = Common.time();
                    state = RAM_SWITCH;
                }
                break;
            case RAM_SWITCH:
                autoDrive(0.6, 0);
                Common.debug("Ramming switch");
                if (Common.time() - timer >= 1500) {
                    autoDrive(0, 0);
                    state = ARM_DOWN;
                }
                break;
            case ARM_DOWN:
                this.arm.armSafety(0.3);
                autoDrive(0, 0);
                Common.debug("Moving arm down");
                if (this.arm.GetDegree() >= 45) {
                    this.arm.armSafety(0);
                    timer = Common.time();
                    state = THROW;
                }
                break;
            case THROW:
                this.intake.setMotor(-0.65);
                autoDrive(0, 0);
                Common.debug("Throwing");
                if (Common.time() - timer >= 1000) {
                    this.intake.setMotor(0);
                    state = IDLE;
                }
                break;
            }
        } else {
            switch (state) {
            case IDLE:
                autoDrive(0, 0);
                break;
            case 1:
                timer = Common.time();
                state = 2;
                autoDrive(0.65, 0);
                Common.debug("State zero");
                break;
            case 2:
                autoDrive(0.65, 0);
                if (Common.time() - timer >= 2000) {
                    state = IDLE;
                }
                break;

            }
        }
    }
}