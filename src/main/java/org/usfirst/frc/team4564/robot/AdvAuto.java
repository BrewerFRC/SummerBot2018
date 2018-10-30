package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdvAuto{
    /**
    * An auto system that references both heading and an encoder to more precisely move around an FRC match.
    *
    * @author Brewer FIRST Robotics Team 4564
    * @author Cooper Parlee; CooperParlee
    */
    public static final int ORIENT = 1;
    public static final int DRIVE = 2;

    DriveTrain dt;
    Heading heading;
    double[] buffer; //Action type, amount
    double dist = 0;

    private int position = 1;

    public AdvAuto(DriveTrain dt){
        this.dt = dt;

        heading = new Heading();
        heading.setPID(Constants.HEADINGP, Constants.HEADINGI, Constants.HEADINGD);

        buffer = new double[] {0};
    }

    public void update(){
        SmartDashboard.putNumber("Left encoder counts", dt.getLeftCounts());
        auto();
    }

    public void calibrate(){
        heading.calibrate();
    }

    public void objectiveBufferTurn(double deg){
        buffer[buffer.length] = ORIENT;
        buffer[buffer.length] = deg;
    }

    public void objectiveBufferMove(){

    }

    private double auto(){
        switch((int)buffer[position * 2 - 1]){
            case ORIENT:
                redefineOrientation(position * 2);
            case DRIVE:
                double target = buffer[position * 2];
                double error = dt.getLeftDist() - target;

                return min(error * Constants.DRIVE_P);
        }
        position++;
    }
    private void redefineOrientation(double deg){
        heading.setAngle(deg);
    }
}
