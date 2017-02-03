package com.minghuiliu.base.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by kevin on 1/31/17.
 */
public class Utils {
    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vector3f[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Constants.VERTEX_SIZE);

        for (Vector3f v : vertices) {
            buffer.put(v.getX());
            buffer.put(v.getY());
            buffer.put(v.getZ());
        }

        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                buffer.put(matrix.get(i, j));

        buffer.flip();
        return buffer;
    }
}
