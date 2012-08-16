uniform sampler2D fbo_texture;
varying vec2 f_texcoord;

void main()
{
	vec4 sum = vec4(0);
	vec2 texcoord = f_texcoord;
	int j;
	int i;
	
	for( i= -3 ;i < 3; i++)
	{
		for (j = -3; j < 3; j++)
		{
			sum += texture2D(fbo_texture, texcoord + vec2(j, i)*0.004) * 0.25;
		}
	} 
	if (texture2D(fbo_texture, texcoord).r < 0.3)
	{
		gl_FragColor = sum*sum*0.012 + texture2D(fbo_texture, texcoord);
	}
	else
	{
		if (texture2D(fbo_texture, texcoord).r < 0.5)
		{
			gl_FragColor = sum*sum*0.009 + texture2D(fbo_texture, texcoord);
		}
		else
		{
			gl_FragColor = sum*sum*0.0075 + texture2D(fbo_texture, texcoord);
		}
	}
}