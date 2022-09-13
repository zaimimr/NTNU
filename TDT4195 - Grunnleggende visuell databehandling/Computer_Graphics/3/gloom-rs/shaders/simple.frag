#version 430 core

in vec4 fragmentColor;
in vec3 fragmentNormals;

out vec4 color;

vec3 lightDirection = normalize(vec3(0.8, -0.5, 0.6));

void main()
{
    color = vec4(fragmentColor.xyz * max(0, dot(fragmentNormals, -lightDirection)), fragmentColor.w);
}


