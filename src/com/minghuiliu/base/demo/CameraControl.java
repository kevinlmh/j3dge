package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by kevin on 2/3/17.
 */
public class CameraControl extends Game {
    private Mesh mesh;
    private Shader shader;
    private Pipeline pipeline;

    float temp = 0.0f;

    double centerX, centerY;
    double prevX, prevY;

    public CameraControl(long window) {
        super(window);

        this.mesh = ResourceLoader.loadMesh("monkey.obj");
        this.shader = new Shader();
        this.pipeline = new Pipeline();


        // Get window dimensions
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(window, width, height);
        prevX = centerX = width[0] / 2.0;
        prevY = centerY = height[0] / 2.0;
        // Set cursor to center of window
        glfwSetCursorPos(window, centerX, centerY);


        pipeline.setCamera(new Camera(new Vector3f(1.0f, 1.0f, -3.0f),
                new Vector3f(0.0f, 0, 1.0f),
                new Vector3f(0, 1, 0)));
        pipeline.setTranslation(0, 0, 5);

        shader.addVertexShader(ResourceLoader.loadShader("mvp.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("triangle.fs"));
        shader.compileShader();

        shader.addUniform("MVP");
    }

    @Override
    public void handleInput() {
        Camera camera = pipeline.getCamera();

        double[] X = new double[1];
        double[] Y = new double[1];
        glfwGetCursorPos(window, X, Y);

        double deltaX = X[0] - prevX;
        double deltaY = Y[0] - prevY;

        float horizontalAngle = (float)(Time.GetDelta() * Constants.MOUSE_SPEED * deltaX);
        float verticalAngle   = (float)(Time.GetDelta() * Constants.MOUSE_SPEED * deltaY);

        camera.rotateX(verticalAngle);
        camera.rotateY(horizontalAngle);

        prevX = X[0];
        prevY = Y[0];

        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS) {
            camera.moveForward(Constants.MOVEMENT_SPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS) {
            camera.moveBackward(Constants.MOVEMENT_SPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            camera.moveLeft(Constants.MOVEMENT_SPEED);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            camera.moveRight(Constants.MOVEMENT_SPEED);
        }
    }


    public void scrollCallback(long window, double xoffset, double yoffset) {
        float fov = pipeline.getCamera().getFov();
        fov = Math.min(120, Math.max(30, fov + 5 * (float)yoffset));
        pipeline.getCamera().setFov(fov);
    }

    @Override
    public void update() {
        double speed = 0.5f;
        temp += speed * (float)Time.GetDelta();
        float sinTemp = (float)Math.sin(temp);

        pipeline.setRotation(0, sinTemp * 180, 0);
    }

    @Override
    public void render() {
        shader.bind();

        Matrix4f MVPMatrix = pipeline.getTransformation();

        shader.setUniform("MVP", MVPMatrix);

        mesh.draw();
    }
}
