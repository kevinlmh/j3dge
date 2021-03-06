#version 330

layout (location = 0) in vec3 position;

out vec4 color;

uniform mat4 MVP;

void main() {
    color = vec4(0.3, 0.3, 0.3, 1.0);
    gl_Position = MVP * vec4(position, 1.0);
}