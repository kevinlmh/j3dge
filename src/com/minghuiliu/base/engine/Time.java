package com.minghuiliu.base.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by kevin on 1/31/17.
 */
public class Time {
    private static double delta;

    public static double GetTime() {
        return glfwGetTime();
    }

    public static double GetDelta() {
        return delta;
    }

    public static void SetDelta(double delta) {
        Time.delta = delta;
    }
}
