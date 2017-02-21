package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/20/17.
 */
public class Material {
    private Texture texture;
    private Vector3f color;

    public Material(Texture texture, Vector3f color) {
        this.texture = texture;
        this.color = color;
    }

    public Material(Texture texture) {
        this(texture, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
