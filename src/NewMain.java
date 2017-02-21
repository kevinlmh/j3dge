import com.minghuiliu.base.demo.PhongLighting;
import com.minghuiliu.base.demo.TextureDemo;
import com.minghuiliu.base.engine.Game;
import com.minghuiliu.base.engine.Time;
import com.minghuiliu.base.engine.Utils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by kevin on 2/20/17.
 */
public class NewMain {

    // The window handle
    private static long window;

    // NEW GAME
    private Game game;

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
//        window = glfwCreateWindow(960, 540, "j3dge", NULL, NULL);
        window = glfwCreateWindow(1280, 720, "j3dge", glfwGetPrimaryMonitor(), NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Setup scroll wheel callback
        glfwSetScrollCallback(window, (long window, double xoffset, double yoffset) -> {
            game.scrollCallback(window, xoffset, yoffset);
        });

        // Set input mode to capture cursor when window is active
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // Init graphics
        Utils.initOpenGL();

        // New instance of a game
        game = new PhongLighting(window);

        // Some time keeping
        double lastFrameTime = glfwGetTime();
        double lastTime = glfwGetTime();
        int frameCount = 0;

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            // Measure speed
            double currentTime = glfwGetTime();
            frameCount++;
            Time.SetDelta(currentTime - lastTime);
            if (currentTime - lastFrameTime >= 1.0) {
                glfwSetWindowTitle(window, "j3dge " + 1000.0/frameCount + " ms/frame (" + frameCount + " FPS)");
                frameCount = 0;
                lastFrameTime = currentTime;
            }

            Utils.clearScreen();

            // MAIN GAME LOOP
            game.handleInput();
            game.update();
            game.render();

            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();

            lastTime = currentTime;
        }
    }

    public static void main(String[] args) {
        System.out.println("LWJGL " + Version.getVersion() + "!");

        NewMain nm = new NewMain();
        nm.init();
        nm.loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

}