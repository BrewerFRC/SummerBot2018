package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AdvAuto{
    /**
    * An auto system that references both heading and an encoder to accurately move around an FRC match.
    *
    * @author Brewer FIRST Robotics Team 4564
    * @author Cooper Parlee
    */

    DriveTrain dt;
    Heading heading;

    public AdvAuto(DriveTrain dt, Heading heading){
        this.dt = dt;
        this.heading = heading;
    }

    public void update(){
        SmartDashboard.putNumber("Left encoder counts", dt.getLeftCounts());
    }



}
