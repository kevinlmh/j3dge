#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 UVCoord;
layout (location = 2) in vec3 vnormal;

out vec2 texCoord;
out vec3 normal;
out vec3 worldPos;

uniform mat4 model;
uniform mat4 MVP;

void main() {
    gl_Position = MVP * vec4(position, 1.0);
    texCoord = UVCoord;
    normal = (model * vec4(vnormal, 0.0)).xyz;
    worldPos = (model * vec4(position, 1.0)).xyz;
}