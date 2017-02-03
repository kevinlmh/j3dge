package com.minghuiliu.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by kevin on 1/31/17.
 */
public class Mesh {
    // Vertex Array Object
    // Required for 3.2+ Core
    private int vao;
    private int vbo;
    private int ibo;
    private int size;

    public Mesh() {
        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);
        this.vbo = glGenBuffers();
        this.ibo = glGenBuffers();
        this.size = 0;
    }

    public void addVertices(Vector3f[] vertices, int[] indices) {
        this.size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void draw() {
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Constants.VERTEX_SIZE * 4, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
    }
}
