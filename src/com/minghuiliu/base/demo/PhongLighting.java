package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**
 * Created by kevin on 2/20/17.
 */
public class PhongLighting extends Game {
    private Mesh mesh;
    private PhongShader shader;
    private Pipeline pipeline;
    private Material material;

    float temp = 0.0f;

    double centerX, centerY;
    double prevX, prevY;

    public PhongLighting(long window) {
        super(window);

        this.mesh = new Mesh();
        this.material = new Material(ResourceLoader.loadTexture("grassblock.png"));
        this.shader = PhongShader.getInstance();
        this.pipeline = new Pipeline();

        Vertex[] vertices = new Vertex[] {
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(0.5f, 0.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(0.0f, 0.5f)),
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.5f, 1.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f), new Vector2f(0.5f, 0.5f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector2f(1.0f, 0.5f)),
        };

        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 6, 15, 4, 6, 14,
                // Bottom face
                16, 19, 18, 17, 19, 16,
                // Back face
                4, 7, 6, 5, 7, 4
        };


        mesh.addVertices(vertices, indices, true);

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

        PhongShader.setAmbientLight(new Vector3f(0.15f, 0.15f, 0.15f));
        PhongShader.setDirectionalLight(new DirectionalLight(
                new BaseLight(new Vector3f(1, 1, 1), 0.8f),
                new Vector3f(1, 1, 1)));
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

    public void keyCallback(long window, int key, int scancode, int action, int mods) {
        // Toggle ambient light
        if ( key == GLFW_KEY_A && action == GLFW_RELEASE ) {
            if (PhongShader.getAmbientLight().getX() > 0)
                PhongShader.setAmbientLight(new Vector3f(0, 0, 0));
            else
                PhongShader.setAmbientLight(new Vector3f(0.15f, 0.15f, 0.15f));
        }
        // Toggle diffuse light
        if (key == GLFW_KEY_D && action == GLFW_RELEASE) {
            if (PhongShader.getDirectionalLight().getBase().getIntensity() > 0)
                PhongShader.getDirectionalLight().getBase().setIntensity(0);
            else
                PhongShader.getDirectionalLight().getBase().setIntensity(0.8f);
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

        Matrix4f MVPMatrix = pipeline.getMVPMatrix();
        Matrix4f ModelMatrix = pipeline.getModelMatrix();

        shader.updateUniforms(ModelMatrix, MVPMatrix, material);

        // Texture binding moved to Shader.updateUniforms();

        mesh.drawTextured();
    }
}
