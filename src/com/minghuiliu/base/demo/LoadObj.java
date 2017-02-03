package com.minghuiliu.base.demo;

import com.minghuiliu.base.engine.*;

/**
 * Created by kevin on 2/2/17.
 */
public class LoadObj extends Game {
    private Mesh mesh;
    private Shader shader;
    private Transform transform;

    float temp = 0.0f;

    public LoadObj() {
        super();

        this.mesh = ResourceLoader.loadMesh("monkey.obj");
        this.shader = new Shader();
        this.transform = new Transform();


        shader.addVertexShader(ResourceLoader.loadShader("loadObj.vs"));
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
        temp += speed * (float)Time.getDelta();
        float sinTemp = (float)Math.sin(temp);

        transform.setTranslation(sinTemp, 0, 0);
        transform.setRotation(0, sinTemp * 180, 0);
    }

    @Override
    public void render() {
        shader.bind();
        shader.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }
}
