import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by kevin on 1/31/17.
 */
public class Time {
    public static double getTime() {
        return glfwGetTime();
    }
}
