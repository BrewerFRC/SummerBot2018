package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private static final int IDLE = 0, RECIEVE = 1, HOLD = 2, THROW = 3;
    public static int state = IDLE;
    public static final Spark INTAKE2 = new Spark(Constants.INTAKE_1);
    public static final Spark INTAKE1 = new Spark(Constants.INTAKE_2);

    public static final SpeedControllerGroup INTAKEMOT = new SpeedControllerGroup(INTAKE1, INTAKE2);

    public static double RECIEVE_SPEED = -0.65f;
    public static double HOLD_SPEED = -0.15f;
    public static double SOFT_THROW_SPEED = 0.6f;

    private Proximity irInput;

    private double previousReading = 0;

    private final double FULL_LOAD = 3, PARTIAL_LOAD = 10;

    public Intake() {
        irInput = new Proximity(Constants.IR_PORT);
        reset();
    }

    public void update() {
        doIntake();
        SmartDashboard.putNumber("State", state);
    }

    public void reset() {
        state = IDLE;
    }

    private void doIntake() {
        switch (state) {
        case IDLE:
            INTAKEMOT.set(0);
            if (Interrupts.getAButton() == true || isPartiallyLoaded()) {
                state = RECIEVE;
            }
            if (isFullyLoaded()) {
                state = HOLD;
            }
            break;
        case RECIEVE:
            INTAKEMOT.set(RECIEVE_SPEED);
            if (isFullyLoaded() == true) {
                state = HOLD;
            }
            if (Interrupts.getAButton() == true) {
                state = IDLE;
            }
            break;
        case HOLD:
            INTAKEMOT.set(HOLD_SPEED);
            if (Interrupts.getRT() == true) {
                state = THROW;
                Interrupts.setRT(false);
            }
            if (isPartiallyLoaded() == false) {
                state = IDLE;
            }
            break;
        case THROW:
            INTAKEMOT.set(SOFT_THROW_SPEED);

            if (isPartiallyLoaded() == false) {
                state = IDLE;
            }
            break;
        }
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