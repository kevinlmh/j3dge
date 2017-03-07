package com.minghuiliu.base.engine;

import java.util.Arrays;

/**
 * Created by kevin on 2/20/17.
 */
public class PhongShader extends Shader {
    private static PhongShader instance;
    private static Vector3f ambientLight;
    private static DirectionalLight directionalLight;
    private static PointLight[] pointLights;
    private static SpotLight[] spotLights;

    private static final int MAX_POINT_LIGHTS = 4;
    private static final int MAX_SPOT_LIGHTS = 4;

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

        ambientLight = new Vector3f(0.15f, 0.15f, 0.15f);
        directionalLight = new DirectionalLight(
                new BaseLight(new Vector3f(0, 0, 0), 0.0f),
                new Vector3f(0, 0, 0));
        pointLights = new PointLight[] {};
        spotLights = new SpotLight[] {};

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

        for (int i = 0; i < MAX_POINT_LIGHTS; i++) {
            addUniform("pointLights["+i+"].base.color");
            addUniform("pointLights["+i+"].base.intensity");
            addUniform("pointLights["+i+"].attenuation.constant");
            addUniform("pointLights["+i+"].attenuation.linear");
            addUniform("pointLights["+i+"].attenuation.exponent");
            addUniform("pointLights["+i+"].position");
            addUniform("pointLights["+i+"].range");
        }

        for (int i = 0; i < MAX_SPOT_LIGHTS; i++) {
            addUniform("spotLights[" + i + "].pointLight.base.color");
            addUniform("spotLights[" + i + "].pointLight.base.intensity");
            addUniform("spotLights[" + i + "].pointLight.attenuation.constant");
            addUniform("spotLights[" + i + "].pointLight.attenuation.linear");
            addUniform("spotLights[" + i + "].pointLight.attenuation.exponent");
            addUniform("spotLights[" + i + "].pointLight.position");
            addUniform("spotLights[" + i + "].pointLight.range");
            addUniform("spotLights[" + i + "].direction");
            addUniform("spotLights[" + i + "].cutoff");
        }
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

        for (int i = 0; i < pointLights.length; i++)
            setUniform("pointLights[" + i + "]", pointLights[i]);

        for (int i = 0; i < spotLights.length; i++)
            setUniform("spotLights[" + i + "]", spotLights[i]);
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

    public static PointLight[] getPointLights() {
        return PhongShader.pointLights;
    }

    public static void setPointLights(PointLight[] pointLights) {
        if (pointLights.length > MAX_POINT_LIGHTS) {
            System.err.println("Error: number of point lights " + pointLights.length
                    + " exceeded maximum allowed " + MAX_POINT_LIGHTS);
            new Exception().printStackTrace();
            System.exit(1);
        }

        PhongShader.pointLights = pointLights;
    }

    public static SpotLight[] getSpotLights() {
        return PhongShader.spotLights;
    }

    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_SPOT_LIGHTS) {
            System.err.println("Error: number of spot lights " + spotLights.length
                    + " exceeded maximum allowed " + MAX_SPOT_LIGHTS);
            new Exception().printStackTrace();
            System.exit(1);
        }

        PhongShader.spotLights = spotLights;
    }



    public void setUniform(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight) {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniform(String uniformName, PointLight pointLight) {
        setUniform(uniformName + ".base", pointLight.getBaseLight());
        setUniformf(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
        setUniformf(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
        setUniformf(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniformf(uniformName + ".range", pointLight.getRange());
    }

    public void setUniform(String uniformName, SpotLight spotLight) {
        setUniform(uniformName + ".pointLight", spotLight.getPointLight());
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
    }
}
