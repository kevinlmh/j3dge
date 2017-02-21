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

    // Retained so that older demos work
    public void addVertices(Vector3f[] vertices, int[] indices) {
        this.size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    public void addVertices(Vertex[] vertices, int[] indices) {
        addVertices(vertices, indices, false);
    }

    public void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals) {
        if (calcNormals)
            calcNormals(vertices, indices);

        this.size = indices.length;

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW);
    }

    private void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int idx0 = indices[i];
            int idx1 = indices[i+1];
            int idx2 = indices[i+2];

            // take cross product of two vectors in triangle
            Vector3f v1 = vertices[idx1].getPos().sub(vertices[idx0].getPos());
            Vector3f v2 = vertices[idx2].getPos().sub(vertices[idx0].getPos());
            Vector3f normal = v1.cross(v2).normalized();

            // update normal of vertices by adding new normal
            vertices[idx0].setNormal(vertices[idx0].getNormal().add(normal));
            vertices[idx1].setNormal(vertices[idx1].getNormal().add(normal));
            vertices[idx2].setNormal(vertices[idx2].getNormal().add(normal));
        }

        // normalize all vertex normals
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setNormal((vertices[i].getNormal().normalized()));
        }
    }

    public void drawTextured() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
    }

    public void draw() {
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
    }
}
