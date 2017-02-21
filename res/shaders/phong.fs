#version 330

in vec2 texCoord;
in vec3 normal;

out vec4 fragColor;

struct BaseLight {
    vec3 color;
    float intensity;
};
struct DirectionalLight {
    BaseLight base;
    vec3 direction;
};

uniform vec3 baseColor;
uniform vec3 ambientLight;
uniform DirectionalLight directionalLight;
uniform sampler2D sampler;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal) {
    float diffuseFactor = dot(normal, -direction);
    vec4 diffuseColor = vec4(0, 0, 0, 0);
    if (diffuseFactor > 0) {
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;
    }
    return diffuseColor;
}

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal) {
    return calcLight(directionalLight.base, -directionalLight.direction, normal);
}

void main()
{
    vec4 totalLight = vec4(ambientLight, 1);
    vec4 color = vec4(baseColor, 1);
    vec4 textureColor = texture(sampler, texCoord.xy);

    if (textureColor != vec4(0, 0, 0, 0))
        color *= textureColor;

    vec3 n = normalize(normal);
    totalLight += calcDirectionalLight(directionalLight, n);

    fragColor = color * totalLight;
}