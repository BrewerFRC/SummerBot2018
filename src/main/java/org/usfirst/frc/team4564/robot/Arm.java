package org.usfirst.frc.team4564.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Spark;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
    public static final Spark arm = new Spark(Constants.ARM_M);
    AnalogInput pot;
    PositionByVelocityPID veloPID;

    private double target = 90;
    private double position;
    private double lastpos;
    private double curV;
    private double G = 0.15;

    public Arm() {
        // arm = new TalonSRX(Constants.ARM_M);
        // arm = new Spark(Constants.ARM_M);

        pot = new AnalogInput(Constants.ARM_POT);
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
        SmartDashboard.putNumber("Potentiometer value", GetPosition());
        SmartDashboard.putNumber("Potentiometer degree", GetDegree());
        /*
         * curV = position - lastpos; double power = veloPID.calc(position, curV);
         * 
         * // arm.set(ControlMode.PercentOutput, power);
         * 
         * veloPID.update();
         */

    }

    public void setSpeed(double speed) {
        arm.set(-speed * 0.25);
    }

    public void armSafety(double speed) {
        double gC = gCalc();
        SmartDashboard.putNumber("gCalc", gC);

        if (GetDegree() > 75 && speed < gC) {
            arm.setSpeed(gC);

        } else if (GetDegree() < -75 && speed > gC) {
            arm.setSpeed(gC);
        } else {
            arm.setSpeed(speed);
        }

    }

    public double GetPosition() {
        return pot.getVoltage();
    }

    private double gCalc() {
        double radians = GetDegree() * Math.PI / 180;
        double Pg = Math.sin(radians) * G;
        return Pg;
    }

    public double GetDegree() {
        return 90 - (GetPosition() - 0.75) / Constants.ARM_POT_SCALAR;
    }

    // call this for gravity adjusted power
    public void powerArm(double power) {
        double p = power - gCalc();
        armSafety(p);
    }

}