package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by kevin on 2/4/17.
 */
public class TextureDemo extends Game {
    private Mesh mesh;
    private Shader shader;
    private Pipeline pipeline;
    private Texture texture;

    float temp = 0.0f;

    double centerX, centerY;
    double prevX, prevY;

    public TextureDemo(long window) {
        super(window);

        this.mesh = new Mesh();
        this.texture = ResourceLoader.loadTexture("grassblock.png");
        this.shader = new Shader();
        this.pipeline = new Pipeline();

//        Vertex[] vertices = new Vertex[] {
//                new Vertex(new Vector3f(-1.0f,-1.0f,0), new Vector2f(0,0)),
//                new Vertex(new Vector3f(-1.0f,1.0f,0), new Vector2f(0,1.0f)),
//                new Vertex(new Vector3f(1.0f,1.0f,0), new Vector2f(1.0f,1.0f)),
//                new Vertex(new Vector3f(1.0f,-1.0f,0), new Vector2f(1.0f,0))
//        };
//
//        int[] indices = new int[] {
//                0, 1, 2,
//                0, 2, 3
//        };

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


        mesh.addVertices(vertices, indices);

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

        shader.addVertexShader(ResourceLoader.loadShader("texture.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("texture.fs"));

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

    @Override
    public void keyCallback(long window, int key, int scancode, int action, int mods) {

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

//        pipeline.setRotation(0, sinTemp * 180, 0);
    }

    @Override
    public void render() {
        shader.bind();

        Matrix4f MVPMatrix = pipeline.getTransformation();

        shader.setUniform("MVP", MVPMatrix);

        texture.bind();

        mesh.drawTextured();
    }
}
