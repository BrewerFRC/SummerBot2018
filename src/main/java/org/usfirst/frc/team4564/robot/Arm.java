package org.usfirst.frc.team4564.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
    TalonSRX arm;
    AnalogPotentiometer pot;
    PositionByVelocityPID veloPID;

    private double target = 90;
    private double position;
    private double lastpos;
    private double curV;

    public Arm() {
        arm = new TalonSRX(Constants.ARM_M);

        pot = new AnalogPotentiometer(Constants.ARM_POT, Constants.ARM_POT_SCALAR);
        veloPID = new PositionByVelocityPID(Constants.ARM_MIN_DEG, Constants.ARM_MAX_DEG, Constants.MIN_ARM_VELOCITY,
                Constants.MAX_ARM_VELOCITY, Constants.MIN_ARM_ACC, Constants.ARMMAXACCEL, Constants.MIN_ARM_MAGNITUDE,
                "Arm Velocity PID");
        position = GetPosition();
        lastpos = position;

    }

    public void RedefineTarget(float update) {
        target = update;

    }

    public void ArmUpdate() {

        curV = position - lastpos;
        double power = veloPID.calc(position, curV);

        // arm.set(ControlMode.PercentOutput, power);

        veloPID.update();

        SmartDashboard.putNumber("Potentiometer value", pot.get());
    }

    public void SetArm(double speed) {
        arm.set(ControlMode.PercentOutput, speed * 0.25);
    }

    public double GetPosition() {
        return pot.get();
    }

}