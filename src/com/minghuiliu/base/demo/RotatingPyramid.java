package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/2/17.
 */
public class RotatingPyramid extends Game {
    private Mesh mesh;
    private Shader shader;
    private Pipeline pipeline;

    float temp = 0.0f;

    public RotatingPyramid() {
        super();

        this.mesh = new Mesh();
        this.shader = new Shader();
        this.pipeline = new Pipeline();

        Vector3f[] data = new Vector3f[] {
                new Vector3f(-0.5f, -0.5f, 0),
                new Vector3f(0, 0.5f, 0),
                new Vector3f(0.5f, -0.5f, 0),
                new Vector3f(0f, -0.5f, 0.5f)
        };

        int[] indices = new int[] {
                0, 1, 3, 3, 1, 2, 2, 1, 0, 0, 2, 3
        };

        mesh.addVertices(data, indices);

        shader.addVertexShader(ResourceLoader.loadShader("movingTriangle.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("triangle.fs"));
        shader.compileShader();

        shader.addUniform("transform");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update() {
        double speed = 0.5f;
        temp += speed * (float)Time.GetDelta();
        float sinTemp = (float)Math.sin(temp);

        pipeline.setTranslation(sinTemp, 0, 0);
        pipeline.setRotation(0, sinTemp * 180, 0);
        pipeline.setScale(sinTemp, sinTemp, sinTemp);
    }

    @Override
    public void render() {
        shader.bind();
        shader.setUniform("transform", pipeline.getTransformation());
        mesh.draw();
    }
}
