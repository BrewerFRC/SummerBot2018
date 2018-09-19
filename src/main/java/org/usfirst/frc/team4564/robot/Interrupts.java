package org.usfirst.frc.team4564.robot;

public class Interrupts {
    private static boolean a_button = false;
    private static boolean rt_button = false;

    public static boolean getAButton() {
        return a_button;
    }

    public static boolean getRT() {
        return rt_button;
    }

    public static void setAButton(boolean state) {
        a_button = state;
        Common.debug("Setting state to " + state);
    }

    public static void setRT(boolean state) {
        rt_button = state;
    }
}