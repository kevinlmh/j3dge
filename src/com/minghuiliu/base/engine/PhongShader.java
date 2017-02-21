package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/20/17.
 */
public class PhongShader extends Shader {
    private static PhongShader instance;
    private static Vector3f ambientLight;

    public static PhongShader getInstance() {
        if (instance == null)
            instance = new PhongShader();

        return instance;
    }

    private PhongShader() {
        super();

        addVertexShader(ResourceLoader.loadShader("phong.vs"));
        addFragmentShader(ResourceLoader.loadShader("phong.fs"));
        compileShader();

        addUniform("MVP");
        addUniform("baseColor");
        addUniform("ambientLight");
    }

    public void updateUniforms(Matrix4f MVPMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            Utils.unbindTextures();

        setUniform("MVP", MVPMatrix);
        setUniform("baseColor", material.getColor());
        setUniform("ambientLight", ambientLight);
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }
}
