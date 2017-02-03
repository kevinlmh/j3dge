package com.minghuiliu.base.engine;

import java.util.Arrays;

/**
 * Created by kevin on 1/31/17.
 */
public class Matrix4f {
    private float[][] m;

    public Matrix4f() {
        this.m = new float[4][4];
    }

    public Matrix4f initIdentity() {
        m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
        m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
        m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
        m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        initIdentity();
        m[0][3] = x;
        m[1][3] = y;
        m[2][3] = z;

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {
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

        m = rz.mul(ry.mul(rx)).getM();

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        initIdentity();

        m[0][0] = x;
        m[1][1] = y;
        m[2][2] = z;

        return this;
    }

    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                res.set(i, j, m[i][0] * r.get(0, j) +
                        m[i][1] * r.get(1, j) +
                        m[i][2] * r.get(2, j) +
                        m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float[][] getM() {
        return m;
    }

    public float get(int x, int y) {
        return m[x][y];
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (float[] row : m) {
            sb.append(Arrays.toString(row));
            sb.append("\n");
        }
        return "Matrix4f{" +
                "m=" + sb.toString() +
                '}';
    }
}
