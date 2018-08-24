package org.usfirst.frc.team4564.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;

public class Arm {
    TalonSRX arm;
    AnalogPotentiometer pot;
    PositionByVelocityPID veloPID;
    
    private static final float ARMMAXACCEL = 0.7f;

    private double target = 90;
    private double position;
    private double lastpos;

    public Arm(){
        arm = new TalonSRX(Constants.ARM_M);

        pot = new AnalogPotentiometer(Constants.ARM_POT, Constants.ARM_POT_SCALAR);
        veloPID = new PositionByVelocityPID(Constants.ARM_MIN_DEG, Constants.ARM_MAX_DEG, , maxVelocity, minPower, ARMMAXACCEL, minPowerMagnitude, "Arm Velocity PID");
        position = GetPosition();
        lastpos = position;

    }

    public void RedefineTarget(float update){
        target = update;

    }
    public void ArmUpdate(){
        double power = veloPID.calc(position, curVelocity);

        arm.set(ControlMode.PercentOutput, power);

        veloPID.update();
    }

    public double GetPosition(){
        return pot.get();
    }

}