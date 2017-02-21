package com.minghuiliu.base.engine;

/**
 * Created by kevin on 2/1/17.
 */

public class Pipeline {
    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;
    private Camera camera;

    public Pipeline() {
        translation = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
        scale = new Vector3f(1, 1, 1);
        camera = new Camera();
    }

    // will eventually be removed
    public Matrix4f getTransformation() {
        Matrix4f scaleMatrix = InitScale(scale.getX(), scale.getY(), scale.getZ());
        Matrix4f rotationMatrix = InitRotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f translationMatrix = InitTranslation(translation.getX(), translation.getY(), translation.getZ());

        Matrix4f cameraTranslation = InitTranslation(-camera.getPosition().getX(), -camera.getPosition().getY(), -camera.getPosition().getZ());
        Matrix4f cameraRotation = InitCameraTransform(camera.getTarget(), camera.getUp());
        Matrix4f perspectiveProjection = InitPerspectiveProjection(camera.getFov(), camera.getAr(), camera.getzNear(), camera.getzFar());

        return perspectiveProjection.mul(cameraRotation.mul(cameraTranslation.mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)))));
    }

    public Matrix4f getModelMatrix() {
        Matrix4f scaleMatrix = InitScale(scale.getX(), scale.getY(), scale.getZ());
        Matrix4f rotationMatrix = InitRotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f translationMatrix = InitTranslation(translation.getX(), translation.getY(), translation.getZ());

        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    public Matrix4f getMVPMatrix() {
        Matrix4f cameraTranslation = InitTranslation(-camera.getPosition().getX(), -camera.getPosition().getY(), -camera.getPosition().getZ());
        Matrix4f cameraRotation = InitCameraTransform(camera.getTarget(), camera.getUp());
        Matrix4f perspectiveProjection = InitPerspectiveProjection(camera.getFov(), camera.getAr(), camera.getzNear(), camera.getzFar());

        return perspectiveProjection.mul(cameraRotation.mul(cameraTranslation.mul(getModelMatrix())));
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    private static Matrix4f InitTranslation(float x, float y, float z) {
        Matrix4f m = new Matrix4f();

        m.initIdentity();

        m.set(0, 3, x);
        m.set(1, 3, y);
        m.set(2, 3, z);

        return m;
    }

    private static Matrix4f InitRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rx.initIdentity();
        ry.initIdentity();
        rz.initIdentity();

        x = (float)Math.toRadians(x);
        y = (float)Math.toRadians(y);
        z = (float)Math.toRadians(z);

        rz.set(0, 0, (float)Math.cos(z));
        rz.set(0, 1, -(float)Math.sin(z));
        rz.set(1, 0, (float)Math.sin(z));
        rz.set(1, 1, (float)Math.cos(z));

        rx.set(1, 1, (float)Math.cos(x));
        rx.set(1, 2, -(float)Math.sin(x));
        rx.set(2, 1, (float)Math.sin(x));
        rx.set(2, 2, (float)Math.cos(x));

        ry.set(0, 0, (float)Math.cos(y));
        ry.set(0, 2, -(float)Math.sin(y));
        ry.set(2, 0, (float)Math.sin(y));
        ry.set(2, 2, (float)Math.cos(y));

        return rz.mul(ry.mul(rx));
    }

    private static Matrix4f InitScale(float x, float y, float z) {
        Matrix4f m = new Matrix4f();

        m.initIdentity();

        m.set(0, 0, x);
        m.set(1, 1, y);
        m.set(2, 2, z);

        return m;
    }

    private static Matrix4f InitPerspectiveProjection(float fov, float ar, float zNear, float zFar) {
        float tanHalfFOV = (float)Math.tan(Math.toRadians(fov / 2));
        float zRange = zNear - zFar;

        Matrix4f m = new Matrix4f();

        m.set(0, 0, 1.0f / (tanHalfFOV * ar));
        m.set(1, 1, 1.0f / tanHalfFOV);
        m.set(2, 2, (-zNear - zFar) / zRange);
        m.set(3, 2, 1.0f);
        m.set(2, 3, 2.0f * zFar * zNear / zRange);

        return m;
    }

    private static Matrix4f InitCameraTransform(Vector3f target, Vector3f up) {
        Vector3f n = target;
        n.normalize();
        Vector3f u = up;
        u = u.cross(target);
        u.normalize();
        Vector3f v = n.cross(u);

        Matrix4f m = new Matrix4f();

        m.set(0, 0, u.getX());
        m.set(0, 1, u.getY());
        m.set(0, 2, u.getZ());

        m.set(1, 0, v.getX());
        m.set(1, 1, v.getY());
        m.set(1, 2, v.getZ());

        m.set(2, 0, n.getX());
        m.set(2, 1, n.getY());
        m.set(2, 2, n.getZ());
        m.set(3, 3, 1.0f);

        return m;
    }

    private static Matrix4f InitLookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f x, y, z;
        z = eye.sub(target).normalized();
        y = up;
        x = y.cross(z);
        y = z.cross(x);
        x.normalize();
        y.normalize();

        Matrix4f m = new Matrix4f();

        m.set(0, 0, x.getX());
        m.set(1, 0, x.getY());
        m.set(2, 0, x.getZ());
        m.set(3, 0, -x.dot(eye));
        m.set(0, 1, y.getX());
        m.set(1, 1, y.getY());
        m.set(2, 1, y.getZ());
        m.set(3, 1, -y.dot(eye));
        m.set(0, 2, z.getX());
        m.set(1, 2, z.getY());
        m.set(2, 2, z.getZ());
        m.set(3, 2, -z.dot(eye));
        m.set(0, 3, 0);
        m.set(1, 3, 0);
        m.set(2, 3, 0);
        m.set(3, 3, 1.0f);

        return m;
    }

}
