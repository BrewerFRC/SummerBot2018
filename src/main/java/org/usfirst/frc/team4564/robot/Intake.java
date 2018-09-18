package org.usfirst.frc.team4564.robot;

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

public class Intake{
    private static final int
    IDLE = 0,
    RECIEVE = 1,
    HOLD = 2,
    THROW = 3
    ;


    public Intake(){

    }

    public void update(){
        doIntake();
    }

    private void doIntake(){
    }
}