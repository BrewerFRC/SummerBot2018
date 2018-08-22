package org.usfirst.frc.team4564.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Proximity {
    private AnalogInput sensor;

    public Proximity(int port){
        sensor = new AnalogInput(port);

        CheckInfaredSensor();
    }
    public double CheckInfaredSensor(){
        double data = sensor.getValue();

        SmartDashboard.putNumber("Infared Value", data);
        return data;
    }
    public double ToDist(float input){
        double out = 23.1186 * Math.pow(0.999014, input);

        return out;
    }
}