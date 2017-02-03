package com.minghuiliu.base.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by kevin on 1/31/17.
 */
public class Time {
    private static double delta;

    public static double getTime() {
        return glfwGetTime();
    }

    public static double getDelta() {
        return delta;
    }

    public static void setDelta(double delta) {
        Time.delta = delta;
    }
}
