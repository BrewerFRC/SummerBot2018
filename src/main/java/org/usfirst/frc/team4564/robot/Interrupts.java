package org.usfirst.frc.team4564.robot;

public class Interrupts {
    private static boolean a_button = false;
    private static boolean rt_button = false;
    private static boolean b_button = false;
    private static boolean h_throw = false;

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

    public static boolean getBButton() {
        boolean prev = b_button;
        b_button = false;
        return prev;
    }

    public static boolean getHThrow() {
        boolean prev = h_throw;
        h_throw = false;
        return prev;
    }

    public static void setBButton(boolean state) {
        b_button = state;
    }

    public static void setAButton(boolean state) {
        a_button = state;
    }

    public static void setRT(boolean state) {
        rt_button = state;
    }

    public static void setHThrow(boolean state) {
        h_throw = state;
    }

}