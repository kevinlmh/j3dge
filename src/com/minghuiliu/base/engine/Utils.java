package com.minghuiliu.base.engine;

import com.minghuiliu.base.demo.TextureDemo;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.*;

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

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex v : vertices) {
            // put position
            buffer.put(v.getPos().getX());
            buffer.put(v.getPos().getY());
            buffer.put(v.getPos().getZ());
            // put texture coordinates
            buffer.put(v.getTexCoord().getX());
            buffer.put(v.getTexCoord().getY());
            // put normal
            buffer.put(v.getNormal().getX());
            buffer.put(v.getNormal().getY());
            buffer.put(v.getNormal().getZ());
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

    public static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for(int i = 0; i < data.length; i++)
            if(!data[i].equals(""))
                result.add(data[i]);

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static int[] toIntArray(Integer[] data) {
        int[] result = new int[data.length];

        for(int i = 0; i < data.length; i++)
            result[i] = data[i].intValue();

        return result;
    }

    public static void initOpenGL() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Print out openGL version
        System.out.println(glGetString(GL_VERSION));

        // Cull back faces
        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        // Enable depth test
        glEnable(GL_DEPTH_TEST);
        // Accept fragment if it closer to the camera than the former one
        glDepthFunc(GL_LESS);
        // Draw polygon using lines, very cool
//        glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public static void clearScreen() {
        //TODO: Stencil Buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void setTextures(boolean enabled) {
        if(enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    public static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public static String getOpenGLVersion() {
        return glGetString(GL_VERSION);
    }
}
