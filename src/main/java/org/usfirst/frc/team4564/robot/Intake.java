package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;

/*
Idle
    Intake off

    Wait until A button (recieve), partial load (recieve) or full load (hold)
Recieve
    Runs intake motors

    Full load (hold) or A button toggle (idle)
Hold
    Runs intake motors at set lower power

    RT or B (soft throw)
Soft throw
    RT - Soft throw (press and hold)
*/

public class Intake {
    private static final int IDLE = 0, RECIEVE = 1, HOLD = 2, THROW = 3, FULL_THROW = 4;
    public static int state = IDLE;
    public static final Spark INTAKEMOT = new Spark(Constants.INTAKE_1);

    public static double RECIEVE_SPEED = 0.65f;
    public static double HOLD_SPEED = 0.25f;
    public static double SOFT_THROW_SPEED = -0.6f;
    public static final double FULL_THROW_SPEED = -1f;
    private Solenoid armClosed;
    private Solenoid armOpen;
    private Solenoid gearboxSolenoid;

    private Proximity irInput;

    private double previousReading = 0;

    private final double FULL_LOAD = 3, PARTIAL_LOAD = 10;

    public Intake() {
        irInput = new Proximity(Constants.IR_PORT);
        reset();

        armOpen = new Solenoid(Constants.ARM_PNU_OPEN);
        armClosed = new Solenoid(Constants.ARM_PNU_CLOSE);
        gearboxSolenoid = new Solenoid(Constants.GEARBOX_PNU);
    }

    public void update() {
        doIntake();
        SmartDashboard.putNumber("State", state);
    }

    public void reset() {
        state = IDLE;
    }

    public void setMotor(double speed) {
        INTAKEMOT.set(speed);
    }

    private void doIntake() {
        switch (state) {
        case IDLE:
            INTAKEMOT.set(0);
            armClosed();
            if (isFullyLoaded()) {
                state = HOLD;
            }

            if (Interrupts.getAButton() == true) {
                state = RECIEVE;
            }
            break;
        case RECIEVE:
            INTAKEMOT.set(RECIEVE_SPEED);
            if (isFullyLoaded() == true) {
                state = HOLD;
            }

            if (isPartiallyLoaded() == true) {
                armClosed();
            } else {
                armOpen();
            }
            if (Interrupts.getAButton() == true) {
                state = IDLE;
            }
            break;
        case HOLD:
            INTAKEMOT.set(HOLD_SPEED);
            armClosed();
            if (Interrupts.getRT() == true) {
                state = THROW;
                Interrupts.setRT(false);
            }
            if (Interrupts.getHThrow() == true) {
                state = FULL_THROW;
            }
            if (isPartiallyLoaded() == false) {
                state = IDLE;
            }
            break;
        case THROW:
            INTAKEMOT.set(SOFT_THROW_SPEED);
            armClosed();
            if (isPartiallyLoaded() == false && !Interrupts.getBButton() == true) {
                state = IDLE;
            }
            break;

        case FULL_THROW:
            INTAKEMOT.set(FULL_THROW_SPEED);
            armClosed();
            if (isPartiallyLoaded() == false && !Interrupts.getBButton() == true) {
                state = IDLE;
            }
            break;
        }
        if (Interrupts.getBButton()) {
            state = THROW;
        }
    }

    public void armClosed() {
        armClosed.set(true);
        armOpen.set(false);
    }

    private void armOpen() {
        armClosed.set(false);
        armOpen.set(true);
    }

    public double getCubeDistance() {
        double reading = irInput.ToDist((float) irInput.CheckInfaredSensor()) * 0.1 + previousReading * 0.9;
        previousReading = reading;
        return reading;
    }

    public boolean isPartiallyLoaded() {
        return (getCubeDistance() < PARTIAL_LOAD);
    }

    public boolean isFullyLoaded() {
        return (getCubeDistance() < FULL_LOAD);
    }
}