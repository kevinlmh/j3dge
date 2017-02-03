package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/3/17.
 */
public class MVP extends Game {
    private Mesh mesh;
    private Shader shader;
    private Pipeline pipeline;

    float temp = 0.0f;

    public MVP() {
        super();

        this.mesh = ResourceLoader.loadMesh("monkey.obj");
        this.shader = new Shader();
        this.pipeline = new Pipeline();

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

