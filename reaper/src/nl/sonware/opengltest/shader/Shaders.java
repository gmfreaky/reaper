package nl.sonware.opengltest.shader;

import org.lwjgl.opengl.ARBShaderObjects;

public class Shaders {

	public static ShaderProgram post_pass = new ShaderProgram(
			new VertexShader("shaders/post_pass.vert"),
			new FragmentShader("shaders/post_pass.frag"));

	public static ShaderProgram post_bloom = new ShaderProgram(
			new VertexShader("shaders/post_pass.vert"),
			new FragmentShader("shaders/post_bloom.frag"));
	
	public static void unbind() { // sets the program object to 0, unbinding the shader
		ARBShaderObjects.glUseProgramObjectARB(0);
	}
}
