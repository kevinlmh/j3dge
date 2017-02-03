package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/3/17.
 */
public class Camera {
    private Vector3f postion;
    private Vector3f target;
    private Vector3f up;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f postion, Vector3f target, Vector3f up) {
        this.postion = postion;
        this.target = target;
        this.up = up;
    }

    public Vector3f getPostion() {
        return postion;
    }

    public void setPostion(Vector3f postion) {
        this.postion = postion;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }
}
