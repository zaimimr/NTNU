#version 430 core

in layout(location=0) vec3 position;
in layout(location=1) vec4 vertexColor;

uniform layout(location=2) mat4x4 matrixVariable; 
// uniform layout(location=3) float sin; 

out vec4 fragmentColor;

void main()
{

    //mat4x4 matrixVariable = mat4(1);
    //matrixVariable[0][0] = sin;


    gl_Position = matrixVariable * vec4(position, 1.0f);
    fragmentColor = vertexColor;
}