package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/1/17.
 */
public class MovingTriangle extends Game {
    private Mesh mesh;
    private Shader shader;
    private Transform transform;

    float temp = 0.0f;

    public MovingTriangle() {
        super();

        this.mesh = new Mesh();
        this.shader = new Shader();
        this.transform = new Transform();

        Vector3f[] data = new Vector3f[]{
                new Vector3f(-0.5f, -0.5f, 0),
                new Vector3f(0, 0.5f, 0),
                new Vector3f(0.5f, -0.5f, 0)
        };

        mesh.addVertices(data);

        shader.addVertexShader(ResourceLoader.loadShader("movingTriangle.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("movingTriangle.fs"));
        shader.compileShader();

        shader.addUniform("transform");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update() {
        temp += 0.01f;

        transform.setTranslation((float)Math.sin(temp), 0, 0);
        transform.setRotation(0, 0, (float)Math.sin(temp) * 180);
    }

    @Override
    public void render() {
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }
}
