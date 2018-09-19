package org.usfirst.frc.team4564.robot;

public class Interrupts {
    private static boolean a_button = false;
    private static boolean rt_button = false;

    public static boolean getAButton() {
        boolean prev = a_button;
        a_button = false;
        return prev;
    }

    public static boolean getRT() {
        boolean prev = rt_button;
        rt_button = false;
        return prev;
    }

    public static void setAButton(boolean state) {
        a_button = state;
        Common.debug("Setting state to " + state);
    }

    public static void setRT(boolean state) {
        rt_button = state;
    }
}