uniform sampler2D fbo_texture;
varying vec2 f_texcoord;

void main()
{
	vec4 sum = vec4(0);
	vec2 texcoord = f_texcoord;
	int j;
	int i;
	
	for( i= -4 ;i < 4; i++)
	{
		for (j = -5; j < 5; j++)
		{
			sum += texture2D(fbo_texture, texcoord + vec2(j, i)*0.002) * 0.14;
		}
	}
	
	gl_FragColor = sum*sum*0.010 + texture2D(fbo_texture, texcoord);
}