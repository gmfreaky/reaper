varying vec4 vertColor;

void main(){
	gl_Vertex.x+=sin(gl_Vertex.z/2);
	gl_Vertex.y+=cos(gl_Vertex.z/2);
    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
    vertColor = vec4(0.6, 0.3, gl_Vertex.z/16, 1.0);
}