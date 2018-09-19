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

    private Proximity irInput;

    private double previousReading = 0;

    private final double FULL_LOAD = 5, PARTIAL_LOAD = 7;

    public Intake(){
        irInput = new Proximity(Constants.IR_PORT);
    }

    public void update(){
        doIntake();
    }

    private void doIntake(){
    }

    public double getCubeDistance() {
	  double reading = irInput.CheckInfaredSensor() / 4 * 0.1 + previousReading * 0.9;
	  double inches = (-20.0/575.0)*reading+20;
	  if (inches < 0){
	    inches = 0;
	  }
	  previousReading = reading;
      return inches;
    }
    public boolean isPartiallyLoaded() {
		return (getCubeDistance() < PARTIAL_LOAD);
	}
	public boolean isFullyLoaded() {
		return (getCubeDistance() < FULL_LOAD);
}