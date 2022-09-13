#version 430 core

out vec4 color;

void main()
{
    color = vec4(0.6f, 0.1f, 0.3f, 1.0f);
}



/*     float n = 100.0;
float checker = n/4;
vec2 pos = mod(gl_FragCoord.xy,vec2(n));


vec4 white = vec4(1.0, 1.0, 1.0, 1.0);
vec4 black = vec4(0.0, 0.0, 0.0, 1.0);

if ((pos.x > checker)&&(pos.y > checker)){
    gl_FragColor=white;
}

if ((pos.x < checker)&&(pos.y < checker)){
    gl_FragColor=white;
}

if ((pos.x < checker)&&(pos.y > checker)){
    gl_FragColor=black;
}

if ((pos.x > checker)&&(pos.y < checker)){
    gl_FragColor=black;
} */
