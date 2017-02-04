package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/1/17.
 */
public class Triangle extends Game{
    private Mesh mesh;
    private Shader shader;

    public Triangle(long window) {
        super(window);
        this.mesh = new Mesh();
        shader = new Shader();

        Vector3f[] data = new Vector3f[]{
                new Vector3f(-1, -1, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(1, -1, 0)
        };

        int[] indices = new int[] {
                0, 1, 2
        };

        mesh.addVertices(data, indices);


        shader.addVertexShader(ResourceLoader.loadShader("triangle.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("triangle.fs"));
        shader.compileShader();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        shader.bind();
        mesh.draw();
    }
}
