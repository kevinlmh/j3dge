package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/20/17.
 */
public class PhongShader extends Shader {
    private static PhongShader instance;
    private static Vector3f ambientLight;
    private static DirectionalLight directionalLight;

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

        addUniform("model");
        addUniform("MVP");
        addUniform("baseColor");
        addUniform("ambientLight");
        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("cameraPos");
        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    public void updateUniforms(Matrix4f ModelMatrix, Matrix4f MVPMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            Utils.unbindTextures();

        setUniform("model", ModelMatrix);
        setUniform("MVP", MVPMatrix);
        setUniform("baseColor", material.getColor());
        setUniform("ambientLight", ambientLight);
        setUniformf("specularIntensity", material.getSpecularIntensity());
        setUniformf("specularPower", material.getSpecularPower());
        // You have to set camera position manually in your game's render() loop
        setUniform("directionalLight", directionalLight);
    }

    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    public static DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }

    public void setUniform(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight) {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }
}
