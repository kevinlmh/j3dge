package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/3/17.
 */
public class Camera {
    private Vector3f position;
    private Vector3f target;
    private Vector3f up;

    private float fov;
    private float ar;
    private float zNear;
    private float zFar;

    public Camera() {
        this(new Vector3f(0, 0, 0), new Vector3f(0, 0, -1), new Vector3f(0, 1, 0));
    }

    public Camera(Vector3f position, Vector3f target, Vector3f up) {
        this.position = position;
        this.target = target;
        this.up = up;

        this.fov = 60;
        this.ar = 16.0f/9.0f;
        this.zNear = 0.1f;
        this.zFar = 1000;
    }

    public void moveForward(float amount) {
        position = position.add(target.mul(amount));
    }

    public void moveBackward(float amount) {
        position = position.sub(target.mul(amount));
    }

    public void moveLeft(float amount) {
        Vector3f left = target.cross(up).normalized();
        position = position.add(left.mul(amount));
    }

    public void moveRight(float amount) {
        Vector3f right = up.cross(target).normalized();
        position = position.add(right.mul(amount));
    }

    public void rotateY(float angle) {
        Vector3f v = new Vector3f(0, 1, 0);
        Vector3f h = v.cross(target).normalized();
        target.rotate(angle, v).normalize();
        up = target.cross(h).normalized();
    }

    public void rotateX(float angle) {
        Vector3f v = new Vector3f(0, 1, 0);
        Vector3f h = v.cross(target).normalized();
        target.rotate(angle, h).normalize();
        up = target.cross(h).normalized();
    }


    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f postion) {
        this.position = postion;
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

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getzNear() {
        return zNear;
    }

    public void setzNear(float zNear) {
        this.zNear = zNear;
    }

    public float getzFar() {
        return zFar;
    }

    public void setzFar(float zFar) {
        this.zFar = zFar;
    }

    public float getAr() {
        return ar;
    }

    public void setAr(float ar) {
        this.ar = ar;
    }
}
