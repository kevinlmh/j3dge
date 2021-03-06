package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/2/17.
 */
public class LoadObj extends Game {
    private Mesh mesh;
    private Shader shader;
    private Pipeline pipeline;

    float temp = 0.0f;

    public LoadObj(long window) {
        super(window);

        this.mesh = ResourceLoader.loadMesh("monkey.obj");
        this.shader = new Shader();
        this.pipeline = new Pipeline();

        shader.addVertexShader(ResourceLoader.loadShader("loadObj.vs"));
        shader.addFragmentShader(ResourceLoader.loadShader("triangle.fs"));
        shader.compileShader();

        shader.addUniform("transform");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void keyCallback(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void scrollCallback(long window, double xoffset, double yoffset) {

    }

    @Override
    public void update() {
        double speed = 0.5f;
        temp += speed * (float)Time.GetDelta();
        float sinTemp = (float)Math.sin(temp);

        pipeline.setTranslation(sinTemp, 0, -3);
        pipeline.setRotation(0, sinTemp * 180, 0);
    }

    @Override
    public void render() {
        shader.bind();
        shader.setUniform("transform", pipeline.getTransformation());
        mesh.draw();
    }
}
