#version 330

in vec2 texCoord;

out vec4 fragColor;

uniform sampler2D sampler;

void main()
{
    fragColor = texture(sampler, texCoord.xy);
}